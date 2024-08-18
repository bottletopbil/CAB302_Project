package com.example.addressbook;

import java.util.ArrayList;
import java.util.List;

public class MockContactDAO implements IContactDAO {
    private static final List<Contact> contacts = new ArrayList<>();
    private static int nextId = 1;

    public MockContactDAO() {
        // Add some initial contacts
        addContact(new Contact("John", "Doe", "johndoe@example.com", "1234567890"));
        addContact(new Contact("Jane", "Doe", "janedoe@example.com", "0987654321"));
    }

    @Override
    public void addContact(Contact contact) {
        contact.setId(nextId++);
        contacts.add(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == contact.getId()) {
                contacts.set(i, contact);
                break;
            }
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        contacts.removeIf(c -> c.getId() == contact.getId());
    }

    @Override
    public Contact getContact(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }
}
