package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    // データベースからすべての連絡先を取得する
    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    // データベースに保存されている連絡先の数を返す
    public long countContacts() {
        return contactRepository.count();
    }

    // データベースから連絡先を削除する
    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    // データベースに連絡先を保存する
    public void saveContact(Contact contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    // データベースからすべての会社を取得する
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    // データベースからすべてのステータスを取得する
    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}
