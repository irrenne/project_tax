//package com.epam.tax.test.db;
//
//import com.epam.tax.dao.impl.ReportDao;
//import com.epam.tax.dao.impl.UserDao;
//import com.epam.tax.entities.Report;
//import com.epam.tax.entities.Role;
//import com.epam.tax.entities.User;
//import com.epam.tax.services.impl.ReportServiceImpl;
//import org.junit.jupiter.api.*;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class DBTest {
//
//    @Test
//    void testInsertAndGetUsers() throws SQLException {
//        List<User> users = createAndInsertUsers();
//        List<User> usersFromDB = UserDao.findAllUsers();
//        assertEquals(users, usersFromDB);
//    }
//
//    @Test
//    void testFindUserByIdAndLogin() throws SQLException {
//        User user1 = UserDao.getUserById(1L);
//        User user2 = UserDao.getUserByLogin("log5");
//
//        User user3 = UserDao.getUserById(2L);
//        User user4 = UserDao.getUserByLogin("log6");
//
//        assertEquals(user1, user2);
//        assertEquals(user3, user4);
//    }
//
//    @Test
//    void testInsertAndGetReports() throws SQLException {
//        List<Report> reports = createAndInsertReports();
//        List<Report> reportsFromDB = ReportDao.findAllReports();
//        assertEquals(reports, reportsFromDB);
//    }
//    @Test
//    void testI123() throws SQLException {
//        List<User> reportsFromDB = UserDao.findAllUsers();
//        System.out.println(reportsFromDB);
//       // assertEquals(reports, reportsFromDB);
//    }
//
//    @Test
//    void test() throws SQLException, IOException {
//        Report report1 = Report.createReport("report1", Date.valueOf(LocalDate.of(2026, 1, 23)));
//        report1.setStatusId(2);
//        report1.setUserId(1L);
//        ReportServiceImpl reportService = new ReportServiceImpl();
////        reportService.insert(report1);
//    }
//
//    private List<User> createAndInsertUsers() throws SQLException {
//        User user1 = User.createUser("log5", "user5", "surname5", "111");
//        User user2 = User.createUser("log6", "user6", "surname6", "222");
//        List<User> users = List.of(new User[]{user1, user2});
//        user1.setRole(Role.CLIENT);
//        user2.setRole(Role.INSPECTOR);
//
//        for (User user : users) {
//            UserDao.insertUser(user);
//        }
//        return users;
//    }
//
//    private List<Report> createAndInsertReports() throws SQLException {
//        User user1 = User.createUser("log3", "user3", "surname3", "111");
//        user1.setRole(Role.CLIENT);
//        UserDao.insertUser(user1);
//
////        Report report1 = Report.createReport("report1", Date.valueOf(LocalDate.of(2026, 1, 23)));
////        Report report2 = Report.createReport("report2", Date.valueOf(LocalDate.of(2025, 11, 18)));
////        List<Report> reports = List.of(new Report[]{report1, report2});
//
////        for (Report report : reports) {
////            report.setStatusId(0);
////            report.setUserId(user1.getId());
////            ReportDao.insertReport(report);
////        }
////        return reports;
//    }
//
//
//}
//
//
////    private static Connection con;
////    private DBManager dbm;
////
////    @BeforeAll
////    static void globalSetUp() throws SQLException, IOException {
////        con = DBManager.getInstance().getConnection();
////    }
////
////    @AfterAll
////    static void globalTearDown() throws SQLException, IOException {
////        con.close();
////    }
////
////
////
////    @BeforeEach
////    void setUp() throws SQLException {
////        dbm = DBManager.getInstance();
////
////        con.createStatement().executeUpdate(DROP_DB_TEST);
////        con.createStatement().executeUpdate(CREATE_DB_TEST);
////        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
////        con.createStatement().executeUpdate(CREATE_USERS_TABLE);
////        con.createStatement().executeUpdate(CREATE_REPORT_TABLE);
////    }
////
////    @AfterEach
////    void tearDown() throws SQLException {
////        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
////        con.createStatement().executeUpdate(DROP_USERS_TABLE);
////        con.createStatement().executeUpdate(DROP_REPORT_TABLE);
////    }