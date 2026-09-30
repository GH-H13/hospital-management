package com.hospital.web;

import com.hospital.entity.ChargeItem;
import com.hospital.service.ChargeItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 收费项目管理Servlet
 */
@WebServlet("/chargeItem/*")
public class ChargeItemServlet extends HttpServlet {

    private final ChargeItemService chargeItemService = new ChargeItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String path = uri.substring(request.getContextPath().length() + "/chargeItem".length());

        if (path.equals("/") || path.equals("/list")) {
            listChargeItems(request, response);
        } else if (path.equals("/add")) {
            showAddForm(request, response);
        } else if (path.equals("/edit")) {
            showEditForm(request, response);
        } else if (path.equals("/delete")) {
            deleteChargeItem(request, response);
        } else {
            listChargeItems(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        String path = uri.substring(request.getContextPath().length() + "/chargeItem".length());

        if (path.equals("/save")) {
            addChargeItem(request, response);
        } else if (path.equals("/update")) {
            updateChargeItem(request, response);
        }
    }

    /**
     * 列表显示
     */
    private void listChargeItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("chargeItems", chargeItemService.findAll());
        request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/list.jsp").forward(request, response);
    }

    /**
     * 显示添加表单
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/add.jsp").forward(request, response);
    }

    /**
     * 显示编辑表单
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer itemId = Integer.parseInt(request.getParameter("itemId"));
            ChargeItem item = chargeItemService.findById(itemId);
            if (item != null) {
                request.setAttribute("chargeItem", item);
                request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/edit.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/chargeItem/list");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/chargeItem/list");
        }
    }

    /**
     * 添加收费项目
     */
    private void addChargeItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ChargeItem item = new ChargeItem();
            item.setItemName(request.getParameter("itemName"));
            item.setItemType(request.getParameter("itemType"));
            item.setDefaultPrice(Double.parseDouble(request.getParameter("defaultPrice")));

            boolean success = chargeItemService.add(item);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/chargeItem/list");
            } else {
                request.setAttribute("error", "添加失败，可能项目名称已存在");
                request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/add.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/add.jsp").forward(request, response);
        }
    }

    /**
     * 修改收费项目
     */
    private void updateChargeItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ChargeItem item = new ChargeItem();
            item.setItemId(Integer.parseInt(request.getParameter("itemId")));
            item.setItemName(request.getParameter("itemName"));
            item.setItemType(request.getParameter("itemType"));
            item.setDefaultPrice(Double.parseDouble(request.getParameter("defaultPrice")));

            boolean success = chargeItemService.update(item);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/chargeItem/list");
            } else {
                request.setAttribute("error", "更新失败");
                request.setAttribute("chargeItem", item);
                request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/chargeItem/edit.jsp").forward(request, response);
        }
    }

    /**
     * 删除收费项目
     */
    private void deleteChargeItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer itemId = Integer.parseInt(request.getParameter("itemId"));
            chargeItemService.delete(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/chargeItem/list");
    }
}
