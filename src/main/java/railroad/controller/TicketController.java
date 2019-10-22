package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.Ticket;
import railroad.service.TicketService;
import java.util.List;

@Controller
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/alltickets", method = RequestMethod.GET)
    public ModelAndView allTickets() {
        List<Ticket> tickets = ticketService.allTickets();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tickets");
        modelAndView.addObject("ticketsList", tickets);
        return modelAndView;
    }

    @RequestMapping(value = "/addticket", method = RequestMethod.POST)
    public ModelAndView addTicket(@ModelAttribute("ticket") Ticket ticket) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        ticketService.add(ticket);
        return modelAndView;
    }
}
