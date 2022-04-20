package com.sheryians.major.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.globel.GlobelData;
import com.sheryians.major.model.Product;
import com.sheryians.major.model.Role;
import com.sheryians.major.services.CategoryService;
import com.sheryians.major.services.ProductService;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("cartCount",GlobelData.cart.size());
		return "index";
	}

	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("cartCount",GlobelData.cart.size());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		return "shop";
	}

	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("cartCount",GlobelData.cart.size());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductByCategory(id));
		return "shop";
	}

	@GetMapping("/shop/viewproduct/{id}")
	public String viewProductsById(Model model, @PathVariable Long id) {
//		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount",GlobelData.cart.size());
		return "viewProduct";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String removeItem(@PathVariable int index) {
		GlobelData.cart.remove(index);
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total",GlobelData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}

}

