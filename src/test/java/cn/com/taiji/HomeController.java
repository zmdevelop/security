package cn.com.taiji;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.taiji.sys.service.MenuService;
import cn.com.taiji.sys.service.RoleService;
import cn.com.taiji.sys.service.UserService;
//import javax.inject.Inject;



//import cn.com.taiji.sys.domain.Menu;
//import cn.com.taiji.sys.service.MenuService;

@Controller
public class HomeController {
	@Inject
	private RoleService roleService;

	@Inject
	private MenuService menuService;
	
	
	@Autowired
	private UserService userService;
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("/system_frame")
	public String homePage(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		//		model.addAttribute("welcome", "thymeleaf样例");
		return "system_frame";
	}
	


	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model) {
		return "index";
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() 
					+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", 
					"You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}

	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public String image(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires",0);
		BufferedImage img = new BufferedImage(68, 22,  

				BufferedImage.TYPE_INT_RGB);  

		// 得到该图片的绘图对象    

		Graphics g = img.getGraphics();  

		Random r = new Random();  

		Color c = new Color(200, 150, 255);  

		g.setColor(c);  

		// 填充整个图片的颜色    

		g.fillRect(0, 0, 68, 22);  

		// 向图片中输出数字和字母    

		StringBuffer sb = new StringBuffer();  

		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();  

		int index, len = ch.length;  

		for (int i = 0; i < 4; i++) {  
			index = r.nextInt(len);  
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt  
					(255)));  
			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));  
			// 输出的  字体和大小                      
			g.drawString("" + ch[index], (i * 15) + 3, 18);  
			//写什么数字，在图片 的什么位置画    
			sb.append(ch[index]);  
		}  

		request.getSession().setAttribute("j_captcha", sb.toString());  

		try {
			ImageIO.write(img, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}



	
}
