/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.like.PostLikeDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "PostListServlet", urlPatterns = {"/PostListServlet"})
public class PostListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String strPage = request.getParameter("page");
        String actionTitle = "";
        PostDAO postDAO = new PostDAO();
        User user = (User) request.getSession().getAttribute("user");
        int count = 0;
        int pageIndex = 0;
        if (strPage == null)
            pageIndex = 1;
        else
            pageIndex = Integer.parseInt(strPage);
        int start = pageIndex * 8 - (8 - 1);
        int end = pageIndex * 8;
        LinkedHashMap<Post, Integer> postMap = new LinkedHashMap<>();
        if (action.equals("like")) {
            count = postDAO.countPostsThatHasLikes();
            postMap = postDAO.getMostLikes(start, end);
//            postMap.forEach((p, c) -> System.out.println(p.getPostId()));
            actionTitle = "Most Liked Posts";
        }
        else if (action.equals("free")) {
            count = postDAO.countFreePosts();
            ArrayList<Post> postList = postDAO.getFreePosts(start, end);
            for (Post post : postList) {
                int countLike = new PostLikeDAO().countPostLikeByPost(post);
                postMap.put(post, countLike);
            }
            actionTitle = "Free Posts";
        }
        else if(action.equals("recent")){
            actionTitle = "Recent Posts";
            count = postDAO.countPosts();
            List<Post> postList = postDAO.getPosts(start, end);
            for (Post post : postList) {
                int countLike = new PostLikeDAO().countPostLikeByPost(post);
                postMap.put(post, countLike);
            }
        }
        int endPage = count / 8;
//        System.out.println(count);
//        System.out.println(endPage);
        if (count % 8 != 0)
            endPage++;
        System.out.println(endPage);
        request.setAttribute("postList", postMap);
        request.setAttribute("actionTitle", actionTitle);
        request.setAttribute("end", endPage);
        request.setAttribute("action", action);
        request.getRequestDispatcher("posts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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