package com.sheryians.major.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.globel.GlobelData;
import com.sheryians.major.model.Product;
import com.sheryians.major.services.ProductService;

@Controller
public class CartController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable long id ) {
			GlobelData.cart.add(productService.getProductById(id).get());
			return "redirect:/shop";
	}
	
	@GetMapping("/cart")
	public String cartGet(Model model) {
		model.addAttribute("cartCount",GlobelData.cart.size());
		model.addAttribute("total",GlobelData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart",GlobelData.cart);
		
		return "cart";
	}
	
	
	
}
