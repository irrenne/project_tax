package com.epam.tax.test.servlets;

import com.epam.tax.servlets.inspector.DenyServlet;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class DenyServletTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final DenyServlet servlet = new DenyServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        final HttpSession session = mock(HttpSession.class);
        String parameter = "deny";


        String path = "/WEB-INF/jsp/inspector/deny_report.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter(parameter)).thenReturn("1");

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, times(1)).getSession(false);
        verify(dispatcher).forward(request, response);

    }
}
