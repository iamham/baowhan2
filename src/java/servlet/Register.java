/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.User;

/**
 *
 * @author LudjaPae
 */

public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession s = request.getSession();
            String username =request.getParameter("username");
            String pwd1 =request.getParameter("pwd1");
            String email =request.getParameter("email");
           
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String telephone = request.getParameter("telephone");
            String province = request.getParameter("province");
            String hospital = request.getParameter("hospital");
            int ihospital = Integer.parseInt(hospital);
            String msg = "";
            boolean success = false;
            //String profilePIC =request.getParameter("profilePIC");
            
            if(User.getUser(username)!=null){
                msg = "<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\"></button><h4><strong>ผิดพลาด</strong></h4><p>ชื่อผู้ใช้นี้มีผู้ใช้แล้ว กรุณากรอกชื่อผู้ใช้ใหม่</p></div>";
                request.setAttribute("msg","<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button><h4><strong>ผิดพลาด</strong></h4><p>ชื่อผู้ใช้นี้มีผู้ใช้แล้ว กรุณากรอกชื่อผู้ใช้ใหม่</p></div>");
            }else{
                /*User u = new User(12,username ,pwd1 ,email ,firstname ,lastname ,profilePIC ,
                        telephone ,address ,province ,amphur ,zipcode);*/
                if(User.pRegister(username,pwd1,email,firstname,lastname,telephone,province,ihospital,0,1)){
                    request.setAttribute("msg","ลงทะเบียนสำเร็จ กรุณากรอกข้อมูลส่วนตัว");
                    success = true;
                }else{
                    msg = "เกิดข้อผิดพลาด กรูณาลองใหม่อีกครั้ง";
                    request.setAttribute("msg","<div class=\"alert alert-danger alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button><h4><strong>ผิดพลาด</strong></h4><p>เกิดข้อผิดพลาด กรูณาลองใหม่อีกครั้ง</p></div>");
                }
                

            //}
        }
        if(success){
        User u = User.getUser(username);
        s.setAttribute("user", u);
        request.setAttribute("msg","<div class=\"col-sm-6 col-lg-3\"><div class=\"alert alert-success alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button><h4><strong>Success</strong></h4><p>ลงทะเบียนเสร็จเรียบร้อย กรุณาเลือกแพทย์ที่รักษาอยู่ ณ ปัจจุบัน</p></div></div>");
        getServletContext().getRequestDispatcher("/setting").forward(request, response);
        }else{
        getServletContext().getRequestDispatcher("/register1?msg="+msg).forward(request, response);
        }
    }
            
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
