package com.epam.tax.test.servlets;

import com.epam.tax.servlets.inspector.ConfirmServlet;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class ConfirmServletTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final ConfirmServlet servlet = new ConfirmServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        String path = "/WEB-INF/jsp/inspector/reports.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);

    }
}
