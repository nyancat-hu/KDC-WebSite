package top.imwonder.mcauth.controller.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WonderErrorController{

    @RequestMapping("/error/{code}/{msg}")
    public String error(@PathVariable("code") Integer code, @PathVariable("msg") String msg, HttpServletRequest req,
            HttpServletResponse res, Model model) {
        res.setStatus(code == null ? 500 : code);
        model.addAttribute("code", code == null ? 500 : code);
        model.addAttribute("msg", msg);
        String uri = (String) req.getAttribute("javax.servlet.forward.request_uri");
        return uri.contains("api") ? "json" : "error";
    }
}
