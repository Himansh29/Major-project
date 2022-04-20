package com.sheryians.major.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sheryians.major.DTO.ProductDTO;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.services.CategoryService;
import com.sheryians.major.services.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	private CategoryService categoryService;
	
	public AdminController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Autowired 
	private ProductService productService;
	
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
		
	}
	@GetMapping("/admin/categories/add")
	public String addCatAdd(Model model) {
		Category category = new Category();
		model.addAttribute("category",category);
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model mod) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			mod.addAttribute("category",category.get());
			return "categoriesAdd";
		}else {
			return "404";
		}
	}
	
	// Products
	
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products",productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add") 
	public String productAddGet(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String ProductAddPost(@ModelAttribute("productDTO") ProductDTO dto, @RequestParam("productImage") MultipartFile file,
			@RequestParam("imgName") String imgName
			)throws IOException {
		Product product = new Product();
		product.setId(dto.getId()); 
		product.setName(dto.getName());
		product.setCategory(categoryService.getCategoryById(dto.getCategoryId()).get());
		product.setPrice(dto.getPrice());
		product.setWeight(dto.getWeight());
		product.setDescription(dto.getDescription());
		 
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();	
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		
		product.setImageName(imageUUID);
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable Long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setCategoryId(product.getCategory().getId());
		dto.setWeight(product.getWeight());
		dto.setPrice(product.getPrice());
		dto.setDescription(product.getDescription());
		dto.setImageName(product.getImageName());
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("productDTO",dto);
		return "productsAdd" ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
