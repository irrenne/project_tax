package com.epam.tax.test.servlets;

import com.epam.tax.entities.Role;
import com.epam.tax.servlets.program.HomeServlet;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class HomeServletTest {
    @Test
    public void doGetClientTest() throws ServletException, IOException {
        final HomeServlet servlet = new HomeServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);

        String attribute = "role";
        String path = "WEB-INF/jsp/client/client.jsp";

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(attribute)).thenReturn(Role.CLIENT);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, times(1)).getSession();
        verify(dispatcher).forward(request, response);

    }

    @Test
    public void doGetInspectorTest() throws ServletException, IOException {
        final HomeServlet servlet = new HomeServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        final HttpSession session = mock(HttpSession.class);
        String attribute = "role";


        String path = "WEB-INF/jsp/inspector/inspector.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(attribute)).thenReturn(Role.INSPECTOR);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, times(1)).getSession();
        verify(dispatcher).forward(request, response);

    }
}
