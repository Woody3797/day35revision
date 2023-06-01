package ibf2022.csf.day35revisionserver.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class CartController {
    
    @PostMapping(path="/cart")
    public ModelAndView getMethodName(@RequestBody MultiValueMap<String, String> form, HttpSession session) {
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedList<>();
            session.setAttribute("cart", cart);
        }

        String item = form.getFirst("item");
        cart.add(item);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("cart");
        mv.addObject("contents", cart);
        mv.setStatus(HttpStatusCode.valueOf(200));

        return mv;
    }

    @PostMapping(path="/cart")
    public ModelAndView postCartHidden(@RequestBody MultiValueMap<String, String> form) {
        // cart-contents
        List<String> cart;
        String cartContents = form.getFirst("cart-contents");
        if (cartContents == null || cartContents.trim().length() <= 0) {
            cart = new LinkedList<>();
        } else {
            String[] items = cartContents.split(",");
            cart = new LinkedList<>(Arrays.asList(items));
        }

        String item = form.getFirst("item");
        cart.add(item);

        String cartContent = cart.stream().collect(Collectors.joining(","));

        ModelAndView mv = new ModelAndView();
        mv.setViewName("cart-hidden");
        mv.addObject("contents", cart);
        mv.addObject("cart-contents", cartContent);
        mv.setStatus(HttpStatusCode.valueOf(200));

        return mv;
    }
}
