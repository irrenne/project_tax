package com.epam.tax.servlets.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(ContextListener.class);

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext s = sce.getServletContext();
        if (s != null) {
            if (s.getInitParameter("uploadDirectory") != null) {
                s.setAttribute("uploadDirectory", s.getInitParameter("uploadDirectory"));
                log.info("Upload directory was initialized");
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext s = sce.getServletContext();
        if (s.getAttribute("uploadDirectory") != null) {
            s.removeAttribute("uploadDirectory");
            log.info("Upload directory was removed");
        }
    }
}
