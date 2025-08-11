package com.hb.cda.tricount_api.service.impl;

import com.hb.cda.tricount_api.dto.request.SettlementRequestDto;
import com.hb.cda.tricount_api.dto.response.SettlementsResponseDto;
import com.hb.cda.tricount_api.entity.Expense;
import com.hb.cda.tricount_api.entity.Group;
import com.hb.cda.tricount_api.entity.Settlement;
import com.hb.cda.tricount_api.entity.User;
import com.hb.cda.tricount_api.mapper.SettlementMapper;
import com.hb.cda.tricount_api.repository.ExpenseRepository;
import com.hb.cda.tricount_api.repository.GroupRepository;
import com.hb.cda.tricount_api.repository.SettlementRepository;
import com.hb.cda.tricount_api.repository.UserRepository;
import com.hb.cda.tricount_api.service.SettlementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation du service pour la gestion des règlements
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;
    private final SettlementMapper settlementMapper;

    /**
     * Créer un nouveau règlement
     * @param settlementRequestDto les données du règlement à créer
     * @return le règlement créé
     */
    @Override
    public SettlementsResponseDto createSettlement(SettlementRequestDto settlementRequestDto) {
        log.info("Création d'un nouveau règlement");
        
        // Récupérer les entités
        User debtor = userRepository.findById(settlementRequestDto.getDebtorId())
                .orElseThrow(() -> new RuntimeException("Débiteur non trouvé"));
        
        User creditor = userRepository.findById(settlementRequestDto.getCreditorId())
                .orElseThrow(() -> new RuntimeException("Créditeur non trouvé"));
        
        Group group = groupRepository.findById(settlementRequestDto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
        
        // Vérifier que les utilisateurs font partie du groupe
        if (!group.getUsers().contains(debtor) || !group.getUsers().contains(creditor)) {
            throw new RuntimeException("Les utilisateurs doivent faire partie du groupe");
        }
        
        // Convertir et sauvegarder
        Settlement settlement = settlementMapper.toEntity(settlementRequestDto);
        settlement.setDebtor(debtor);
        settlement.setCreditor(creditor);
        settlement.setGroup(group);
        
        Settlement savedSettlement = settlementRepository.save(settlement);
        
        log.info("Règlement créé avec succès avec l'ID : {}", savedSettlement.getId());
        return settlementMapper.toDto(savedSettlement);
    }

    /**
     * Récupérer tous les règlements
     * @return la liste de tous les règlements
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementsResponseDto> getAllSettlements() {
        log.info("Récupération de tous les règlements");
        List<Settlement> settlements = settlementRepository.findAll();
        return settlementMapper.toDtoList(settlements);
    }

    /**
     * Récupérer un règlement par son ID
     * @param id l'ID du règlement
     * @return le règlement trouvé
     */
    @Override
    @Transactional(readOnly = true)
    public SettlementsResponseDto getSettlementById(String id) {
        log.info("Récupération du règlement avec l'ID : {}", id);
        
        Settlement settlement = settlementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Règlement non trouvé avec l'ID : " + id));
        
        return settlementMapper.toDto(settlement);
    }

    /**
     * Récupérer tous les règlements d'un groupe
     * @param groupId l'ID du groupe
     * @return la liste des règlements du groupe
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementsResponseDto> getSettlementsByGroupId(String groupId) {
        log.info("Récupération des règlements du groupe : {}", groupId);
        
        List<Settlement> settlements = settlementRepository.findByGroup_Id(groupId);
        return settlementMapper.toDtoList(settlements);
    }

    /**
     * Récupérer tous les règlements d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des règlements de l'utilisateur
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementsResponseDto> getSettlementsByUserId(String userId) {
        log.info("Récupération des règlements de l'utilisateur : {}", userId);
        
        List<Settlement> settlements = settlementRepository.findByUserId(userId);
        return settlementMapper.toDtoList(settlements);
    }

    /**
     * Calculer les règlements pour un groupe donné
     * @param groupId l'ID du groupe
     * @return la liste des règlements calculés
     */
    @Override
    @Transactional
    public List<SettlementsResponseDto> calculateSettlementsForGroup(String groupId) {
        log.info("Calcul des règlements pour le groupe : {}", groupId);
        
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
        
        // Supprimer les anciens règlements du groupe
        settlementRepository.deleteByGroup_Id(groupId);
        
        // Récupérer toutes les dépenses du groupe
        List<Expense> expenses = expenseRepository.findByGroup_Id(groupId);
        
        if (expenses.isEmpty()) {
            log.info("Aucune dépense trouvée pour le groupe");
            return new ArrayList<>();
        }
        
        // Calculer les soldes de chaque utilisateur
        Map<String, Double> userBalances = calculateUserBalances(expenses, group.getUsers());
        
        // Créer les règlements pour équilibrer les comptes
        List<Settlement> settlements = createOptimalSettlements(userBalances, group);
        
        // Sauvegarder les règlements
        List<Settlement> savedSettlements = settlementRepository.saveAll(settlements);
        
        log.info("Calculé et sauvegardé {} règlements", savedSettlements.size());
        return settlementMapper.toDtoList(savedSettlements);
    }

    /**
     * Calcule les soldes de chaque utilisateur dans un groupe
     */
    private Map<String, Double> calculateUserBalances(List<Expense> expenses, List<User> users) {
        Map<String, Double> balances = new HashMap<>();
        
        // Initialiser les soldes à 0
        for (User user : users) {
            balances.put(user.getId(), 0.0);
        }
        
        // Calculer les soldes basés sur les dépenses
        for (Expense expense : expenses) {
            String payerId = expense.getPayer().getId();
            double amountPerBeneficiary = expense.getAmount() / expense.getBeneficiary().size();
            
            // Le payeur reçoit le montant total
            balances.put(payerId, balances.get(payerId) + expense.getAmount());
            
            // Chaque bénéficiaire doit sa part
            for (User beneficiary : expense.getBeneficiary()) {
                String beneficiaryId = beneficiary.getId();
                balances.put(beneficiaryId, balances.get(beneficiaryId) - amountPerBeneficiary);
            }
        }
        
        return balances;
    }

    /**
     * Crée les règlements optimaux pour équilibrer les comptes
     */
    private List<Settlement> createOptimalSettlements(Map<String, Double> balances, Group group) {
        List<Settlement> settlements = new ArrayList<>();
        
        // Séparer les créditeurs (solde positif) et débiteurs (solde négatif)
        List<Map.Entry<String, Double>> creditors = balances.entrySet().stream()
                .filter(entry -> entry.getValue() > 0.01) // Éviter les erreurs d'arrondi
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Trier par montant décroissant
                .collect(Collectors.toList());
        
        List<Map.Entry<String, Double>> debtors = balances.entrySet().stream()
                .filter(entry -> entry.getValue() < -0.01) // Éviter les erreurs d'arrondi
                .sorted(Map.Entry.comparingByValue()) // Trier par montant croissant (plus négatif en premier)
                .collect(Collectors.toList());
        
        // Créer les règlements
        int creditorIndex = 0;
        int debtorIndex = 0;
        
        while (creditorIndex < creditors.size() && debtorIndex < debtors.size()) {
            Map.Entry<String, Double> creditor = creditors.get(creditorIndex);
            Map.Entry<String, Double> debtor = debtors.get(debtorIndex);
            
            double creditAmount = creditor.getValue();
            double debtAmount = Math.abs(debtor.getValue());
            double settlementAmount = Math.min(creditAmount, debtAmount);
            
            if (settlementAmount > 0.01) { // Éviter les petits montants
                Settlement settlement = new Settlement();
                settlement.setAmount(Math.round(settlementAmount * 100.0) / 100.0); // Arrondir à 2 décimales
                settlement.setCreditor(userRepository.findById(creditor.getKey()).orElse(null));
                settlement.setDebtor(userRepository.findById(debtor.getKey()).orElse(null));
                settlement.setGroup(group);
                settlement.setComment("Règlement automatique calculé");
                
                settlements.add(settlement);
                
                // Mettre à jour les soldes
                creditor.setValue(creditAmount - settlementAmount);
                debtor.setValue(debtor.getValue() + settlementAmount);
            }
            
            // Passer au créditeur/débiteur suivant si le solde est épuisé
            if (Math.abs(creditor.getValue()) < 0.01) {
                creditorIndex++;
            }
            if (Math.abs(debtor.getValue()) < 0.01) {
                debtorIndex++;
            }
        }
        
        return settlements;
    }

    /**
     * Supprimer un règlement
     * @param id l'ID du règlement à supprimer
     */
    @Override
    public void deleteSettlement(String id) {
        log.info("Suppression du règlement avec l'ID : {}", id);
        
        if (!settlementRepository.existsById(id)) {
            throw new RuntimeException("Règlement non trouvé avec l'ID : " + id);
        }
        
        settlementRepository.deleteById(id);
        log.info("Règlement supprimé avec succès");
    }

    /**
     * Supprimer tous les règlements d'un groupe
     * @param groupId l'ID du groupe
     */
    @Override
    public void deleteAllSettlementsByGroupId(String groupId) {
        log.info("Suppression de tous les règlements du groupe : {}", groupId);
        
        settlementRepository.deleteByGroup_Id(groupId);
        log.info("Tous les règlements du groupe supprimés avec succès");
    }

    /**
     * Récupérer le solde d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return le solde de l'utilisateur
     */
    @Override
    @Transactional(readOnly = true)
    public Double getUserBalance(String userId) {
        log.info("Calcul du solde de l'utilisateur : {}", userId);
        
        Double credits = settlementRepository.sumCreditsByUserId(userId);
        Double debts = settlementRepository.sumDebtsByUserId(userId);
        
        credits = credits != null ? credits : 0.0;
        debts = debts != null ? debts : 0.0;
        
        return credits - debts;
    }
}
