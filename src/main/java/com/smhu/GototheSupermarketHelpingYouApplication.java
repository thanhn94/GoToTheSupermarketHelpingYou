package com.smhu;

import com.smhu.controller.AccountController;
import com.smhu.controller.MarketController;
import com.smhu.controller.OrderController;
import com.smhu.controller.StatusController;
import com.smhu.helper.DateTimeHelper;
import com.smhu.iface.IMain;
import com.smhu.iface.IOrder;
import com.smhu.market.Market;
import com.smhu.order.Order;
import com.smhu.order.OrderDetail;
import com.smhu.order.TimeTravel;
import com.smhu.status.Status;
import com.smhu.utils.DBUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.smhu.controller")
@ComponentScan("com.smhu.schedule")
@EnableScheduling
public class GototheSupermarketHelpingYouApplication {

    MainService service = new MainService();
    IMain mainListener = new MainService();

    public IMain getMainListener() {
        return mainListener;
    }

    public static void main(String[] args) {
        SpringApplication.run(GototheSupermarketHelpingYouApplication.class, args);
    }

    public void init() {
        try {
            Date date = new Date(Calendar.getInstance(
                    TimeZone.getTimeZone("Asia/Ho_Chi_Minh"), Locale.forLanguageTag("vi-vn"))
                    .getTimeInMillis());

            Map<String, String> roles = service.loadRoles();
            List<Order> inqueueOrders = service.loadOrder(date, "12");
            List<Market> markets = service.loadMarket();
            List<Status> listStatus = service.loadStatus();

            if (!roles.isEmpty()) {
                AccountController.mapRoles = roles;
            }

            if (listStatus != null) {
                for (Status status : listStatus) {
                    StatusController.mapStatus.put(status.getCode(), status.getDescription());
                }
            }

            if (markets != null) {
                for (Market market : markets) {
                    MarketController.mapMarket.put(market.getId(), market);
                }
            }

            if (inqueueOrders != null) {
                service.loadOrderDetail(inqueueOrders);
                for (Order order : inqueueOrders) {
                    OrderController.mapOrderInQueue.put(order, new DateTimeHelper().calculateTimeForShipper(order, order.getTimeTravel()));
                    order.setTimeTravel(null);
                }
                service.loadOrderInProcess();
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(GototheSupermarketHelpingYouApplication.class.getName()).log(Level.SEVERE, e.getMessage());
        }

    }

    class MainService implements IMain {

        @Override
        public Map<String, String> loadRoles() throws SQLException, ClassNotFoundException {
            return new AccountController().getAccountListener().getRoles();
        }

        @Override
        public List<Status> loadStatus() throws SQLException, ClassNotFoundException {
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            List<Status> list = null;

            try {
                con = DBUtils.getConnection();
                if (con != null) {
                    String sql = "SELECT CODE, DESCRIPTION\n"
                            + "FROM STATUS\n"
                            + "ORDER BY CODE ASC";
                    stmt = con.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new Status(rs.getInt("CODE"), rs.getString("DESCRIPTION")));
                    }
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            return list;
        }

        public void loadOrderInProcess() {
            IOrder orderListener = new OrderController().getOrderListener();
            try {
                orderListener.checkOrderInqueue();
            } catch (Exception e) {
                Logger.getLogger(GototheSupermarketHelpingYouApplication.class.getName()).log(Level.SEVERE, e.getMessage());
            }

        }

        @Override
        public List<Market> loadMarket() throws SQLException, ClassNotFoundException {
            return new MarketController().getMarketListener().getMarkets();
        }

        @Override
        public List<Order> loadOrder(Date date, String status) throws SQLException, ClassNotFoundException {
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            List<Order> listOrders = null;

            try {
                con = DBUtils.getConnection();
                if (con != null) {
                    String sql = "EXEC GET_ORDERS_INQUEUE_BY_DATE ?, ?";
                    stmt = con.prepareStatement(sql);
                    stmt.setDate(1, date);
                    stmt.setString(2, status);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        if (listOrders == null) {
                            listOrders = new ArrayList<>();
                        }
                        listOrders.add(new Order(rs.getString("ID"),
                                rs.getString("CUST"),
                                rs.getString("ADDRESS_DELIVERY"),
                                rs.getString("NOTE"),
                                rs.getString("MARKET"),
                                null,
                                null,
                                null,
                                rs.getDate("CREATED_DATE"),
                                rs.getTime("CREATED_TIME"),
                                rs.getTime("LAST_UPDATE"),
                                rs.getInt("status"),
                                null,
                                null,
                                rs.getDouble("COST_SHOPPING"),
                                rs.getDouble("COST_DELIVERY"),
                                rs.getDouble("TOTAL_COST"),
                                rs.getDate("DATE_DELIVERY"),
                                rs.getTime("TIME_DELIVERY"),
                                new TimeTravel(rs.getTime("GOING"),
                                        rs.getTime("SHOPPING"),
                                        rs.getTime("DELIVERY"),
                                        rs.getTime("TRAFFIC")),
                                null,
                                null));
                    }
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            return listOrders;
        }

        @Override
        public void loadOrderDetail(List<Order> listOrders) throws SQLException, ClassNotFoundException {
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                con = DBUtils.getConnection();
                if (con != null) {
                    String sql = "EXEC GET_ORDER_DETAIL_BY_ID ?";
                    stmt = con.prepareStatement(sql);
                    for (Order order : listOrders) {
                        stmt.setString(1, order.getId());
                        rs = stmt.executeQuery();
                        List<OrderDetail> listDetails = order.getDetails();
                        while (rs.next()) {
                            if (listDetails == null) {
                                listDetails = new ArrayList<>();
                            }
                            listDetails.add(new OrderDetail(rs.getString("ID"),
                                    rs.getString("NAME"),
                                    rs.getString("IMAGE"),
                                    rs.getDouble("ORIGINAL_PRICE"),
                                    rs.getDouble("PAID_PRICE"),
                                    rs.getDouble("WEIGHT"),
                                    rs.getInt("SALE_OFF")));
                        }
                        order.setDetails(listDetails);
                    }
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
    }
}
