/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author LudjaPae
 */
public class Login extends HttpServlet {

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession s = request.getSession();
        String msg = "";
        boolean success = false;
        int type = 0,status = 0;
        
        if (User.getUser(username) == null) {
            msg = "ไม่พบชื่อผู้ใช้ในระบบ หรือ รหัสผ่านไม่ถูกต้อง";
        } else {
            User u = User.getUser(username);
            if (u.getPassword().equals(User.toMD5(password))) {
                s.setAttribute("user", u);
                type = u.getType();
                status = u.getStatus();
                System.out.println(type);
                success = true;
            } else {
                msg = "ไม่พบชื่อผู้ใช้ในระบบ หรือ รหัสผ่านไม่ถูกต้อง";
            }
        }
        request.setAttribute("msg", msg);
        if (success) {
            if (type == 1) {
                getServletContext().getRequestDispatcher("/dashboard").forward(request, response);
            } else if (type == 2 & status==2) {
                getServletContext().getRequestDispatcher("/docdashboard").forward(request, response);
            } else if (type == 2 & status==1){
                msg = "กรุณารอการยืนยันบัญชีผู้ใช้งานจากผู้ดูแลระบบ เพิ่มเติมติดต่อ admin@baowhan.com";
                request.setAttribute("msg", msg);
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }else if (type == 3) {
                getServletContext().getRequestDispatcher("/admindashboard").forward(request, response);
            }else{
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

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
