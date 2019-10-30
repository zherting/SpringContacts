package dmacc.controller;

import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Contact;
import dmacc.repository.ContactRepository;

@Controller
public class WebController {
	@Autowired
	ContactRepository repo;

	@GetMapping("/viewAll")
	public String viewAllContacts(Model model) {
		model.addAttribute("contacts", repo.findAll());
		return "results";
	}

	@GetMapping("/inputContact")
	public String addNewContact(Model model) {
		Contact c = new Contact();
		model.addAttribute("newContact", c);
		return "input";
	}

	@PostMapping("/inputContact")
	public String addNewContact(@ModelAttribute Contact c, Model model) {
		repo.save(c);
		model.addAttribute("contacts", repo.findAll());
		return "results";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Contact c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("contact", c);
		return "update";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid Contact c, BindingResult result, Model model) {
		if (result.hasErrors()) {
			c.setId(id);
			return "update";
		}
		repo.save(c);
		model.addAttribute("contacts", repo.findAll());
		return "results";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Contact c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));
		repo.delete(c);
		model.addAttribute("contacts", repo.findAll());
		return "results";
	}
}
