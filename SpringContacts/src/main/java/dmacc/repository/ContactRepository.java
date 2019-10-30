package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Contact;

@Repository
public interface ContactRepository  extends JpaRepository<Contact, Long> {

}
