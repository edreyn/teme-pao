package project.service;

import project.model.*;
import project.repository.RepositoryClient;

import java.util.ArrayList;

public class ServiceClient {
    private RepositoryClient repositoryClient = new RepositoryClient();
    private static ServiceClient instanta = new ServiceClient();

    FileWritingClient fileWritingClient = FileWritingClient.getWritingInstanta();
    AuditService auditService = AuditService.getInstanta();


    private ServiceClient() { }

    public static ServiceClient getInstanta() {
        return instanta;
    }

    public void addClient(Client client) {
        repositoryClient.add(client);
        auditService.actiune("Adaugare client nou", auditService.getTimestamp());
        fileWritingClient.scriereClient(client);

    }

    public void showClient(Client client) {
        repositoryClient.showClient(client);
    }

    public Client getClientByNumar(int numar) {
        return repositoryClient.getClientByNumar(numar);
    }

    public ArrayList<Client> getAllClienti() {
        return repositoryClient.getAll();
    }

    public ArrayList<Student> getAllStudenti() {
        return repositoryClient.getAllStudenti();
    }

    public ArrayList<Elev> getAllElevi() {
        return repositoryClient.getAllElevi();
    }

    public ArrayList<Copil> getAllCopii() {
        return repositoryClient.getAllCopii();
    }

    public ArrayList<Pensionar> getAllPensionari() {
        return repositoryClient.getAllPensionari();
    }

    public ArrayList<Client> getClientiFaraDiscount() {
        return repositoryClient.getClientiPretIntreg();
    }

    public ArrayList<Client> getClientiDiscount() {
        return repositoryClient.getClientiDiscount();
    }

    public void removeClientByNumar(int numar) {
        ServiceBilet serviceBilet = ServiceBilet.getInstanta();
        Client client = repositoryClient.getClientByNumar(numar);
        if(client == null) {
            System.out.println("Clientul nu exista.");
        }
        ArrayList<Bilet> bilete = serviceBilet.getBiletByNumarClient(numar);
        for(Bilet bilet : bilete) {
            serviceBilet.anulareBilet(bilet.getNumarBilet());
        }
        repositoryClient.stergere(client);
    }

    public void addClientExistent(Client client) {
        auditService.actiune("Adaugarea unui client existent in fisier", auditService.getTimestamp());
        repositoryClient.add(client);
    }

    public void getClientiDinFisier() {
        auditService.actiune("Ia toti clientii din fisier", auditService.getTimestamp());
        FileReadingClient fileReadingClient = FileReadingClient.getReadingInstanta();
        ArrayList<Client> clienti;
        clienti = fileReadingClient.readClienti();
        for(Client client: clienti) {
            addClientExistent(client);
        }
    }
}