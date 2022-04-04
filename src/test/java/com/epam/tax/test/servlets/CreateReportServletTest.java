package com.epam.tax.test.servlets;

import com.epam.tax.servlets.client.CreateReportServlet;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CreateReportServletTest {

    @Test
    public void doGetTest() throws ServletException, IOException {
        final CreateReportServlet servlet = new CreateReportServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        final HttpSession session = mock(HttpSession.class);

        String path = "WEB-INF/jsp/client/create_report.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, times(1)).getSession();
        verify(dispatcher).forward(request, response);

    }
}
