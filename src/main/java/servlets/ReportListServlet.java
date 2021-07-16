/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import report.Report;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ReportListServlet", urlPatterns = {"/ReportListServlet"})
public class ReportListServlet extends HttpServlet {

    final int numReportInPage = 5;
    final String reportListPage = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNum = 1;
        if (request.getParameter("pageNum") != null) {
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }
        ArrayList<Report> reportList = new ArrayList<>();//change
        
        int numPage = reportList.size() / numReportInPage 
                + (reportList.size() % numReportInPage == 0 ? 0 : 1);
        request.setAttribute("numPage", numPage);
        
        int startIndex = (pageNum - 1) * numReportInPage;
        int endIndex = pageNum * numReportInPage - 1;
        ArrayList<Report> subArray = (ArrayList<Report>) reportList.subList(startIndex, endIndex);
        request.setAttribute("reportList", subArray);
       
        request.getRequestDispatcher(reportListPage).forward(request, response);
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
