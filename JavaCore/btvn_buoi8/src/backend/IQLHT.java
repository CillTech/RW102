package backend;

public interface IQLHT {
        // Chức năng quản lý Account
        void showAllAccounts();
        void searchAccountByUsername(String username);

        // Chức năng quản lý Department
        void showAllDepartments();
        void searchDepartmentByName(String name);
}
