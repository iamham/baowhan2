/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DiabetesLog;
import model.Hospital;
import model.Message;
import model.User;

/**
 *
 * @author sarunpeetasai
 */
public class showMessage extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        SimpleDateFormat sf = new SimpleDateFormat("d/M/yy HH:mm:ss");
        SimpleDateFormat sf1 = new SimpleDateFormat("d");
        User u = (User) session.getAttribute("user");
        String id =request.getParameter("id");
        String pid =request.getParameter("pid");
        if(id==null){
            id = ""+Message.getCid(u.getUserID(), Integer.parseInt(pid));
        }
        User p = User.getUserByID(Message.getPatientID(Integer.parseInt(id), u.getUserID()));
        request.setAttribute("pname", p.getFirstname()+" "+p.getLastname());
        request.setAttribute("pp", p.getProfilePIC());
        request.setAttribute("hospital", "โรงพยาบาล"+Hospital.findById(p.getHospitalID()));
        List<DiabetesLog> log = DiabetesLog.getAllUserRecord(Message.getPatientID(Integer.parseInt(id), u.getUserID()));
        List<Message> mes = Message.getAllMessage(Integer.parseInt(id));
        String stMessage="";
        for(int i=0;i<mes.size();i++){
            stMessage = stMessage.concat("<tr><td><b>"+User.getUserName(mes.get(i).getUser_id_fk())+"</b><br>"+mes.get(i).getMessage()+"</td><td class=\"text-center\" style=\"width: 80px;\"><span class=\"text-muted\">"+sf.format(mes.get(i).getTime())+"</span></td></tr>");
        }
        if(mes.isEmpty()){
            stMessage = stMessage.concat("<center><h4>คุณยังไม่มีข้อความใดๆ ส่งข้อความได้ด้านล่าง</h4></center>");
        }
        
        String chartValue = "[";
        String chartDate = "[";
            int itemp = 1;
            for(int i=0;i<log.size();i++){
                chartValue = chartValue.concat("[\""+itemp+"\", "+log.get(i).getValue()+"],");
                chartDate = chartDate.concat("[\""+itemp+"\", \'"+sf1.format(log.get(i).getChecktime())+"\'],");
                itemp++;
            }
        chartValue = chartValue.concat("];");
        chartDate = chartDate.concat("];");
        request.setAttribute("chartDate", chartDate);
        request.setAttribute("chartValue", chartValue);
        request.setAttribute("msg",  stMessage);
        request.setAttribute("cid", id);
        getServletContext().getRequestDispatcher("/showMessage.jsp").forward(request, response);
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
