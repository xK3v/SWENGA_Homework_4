package at.fh.swenga.jpa.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;

import at.fh.swenga.jpa.dao.GameRepository;
import at.fh.swenga.jpa.model.GameModel;

@Controller
public class ReportController {
	@Autowired
	GameRepository gameRepository;

	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage templateMessage;

	@RequestMapping(value = { "/report" })
	public String report(Model model, @RequestParam(required = false) String excel,
			@RequestParam(required = false) String pdf, @RequestParam(required = false) String mail,
			@RequestParam(name = "gameId", required = false) List<Integer> gameIds) {

		// User didn't select any game ? -> go back to list
		if (CollectionUtils.isEmpty(gameIds)) {
			model.addAttribute("errorMessage", "No games selected!");
			return "forward:/list";
		}

		// Convert the list of ids to a list of games
		// The method findAll() can do this
		List<GameModel> games = gameRepository.findAllById(gameIds);

		// Store the games in the model, so the reports can access them
		model.addAttribute("games", games);

		// Which submit button was pressed? -> call the right report view
		if (StringUtils.isNoneEmpty(excel)) {
			return "excelReport";
		} else if (StringUtils.isNoneEmpty(pdf)) {
			return "pdfReport";
			// return "pdfReportV5";

		} else if (StringUtils.isNoneEmpty(mail)) {
			sendMail(games);
			model.addAttribute("errorMessage", "Mail sent");
			return "forward:/list";
		}

		else {
			return "forward:/list";
		}
	}

	private void sendMail(List<GameModel> games) {

		String content = "";
		for (GameModel game : games) {
			content += game.getName() + " (" + game.getDeveloper() + ")\n";
		}

		// Create a thread safe "copy" of the template message and customize it
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);

		// You can override default settings from dispatcher-servlet.xml:
		// msg.setFrom(from);
		// msg.setTo(to);
		// msg.setSubject(subject);
		msg.setText(String.format(msg.getText(), "Max Mustermann", content));
		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			ex.printStackTrace();
		}
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}

}
