package controller;

import model.ModelUser;
import node.NodeClass.NodeUser;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private ModelUser modelUser;

    public UserController() {
        this.modelUser = new ModelUser();
    }

    public void addUser(String name, String username, String password) {
        if (cekUsername(username)) {
            int userId = modelUser.getLasIdUser();
            userId++;
            NodeUser newUser = new NodeUser(userId, name, username, password);
            modelUser.addUser(newUser);
        } else {
            System.out.println("Username sudah ada, gunakan username lain.");
        }
    }

    public boolean cekUsername(String username) {
        List<NodeUser> userList = modelUser.getUserList();

        if (userList != null) {
            for (NodeUser user : userList) {
                if (user.getUsername().equals(username)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean updateUser(int userId, String newName, String newUsername, String newPassword) {

        NodeUser existingUser = findUserById(userId);

        if (existingUser != null) {
            NodeUser updatedUser = new NodeUser(userId, existingUser.getNama(), existingUser.getUsername(), existingUser.getPassword());
            if (!newName.isEmpty()){
                updatedUser.setNama(newName);
            } else if(!newUsername.isEmpty()){
                updatedUser.setUsername(newUsername);
            } else if (!newPassword.isEmpty()) {
                updatedUser.setPassword(newPassword);
            }

            modelUser.updateUser(updatedUser);
            return true;
        }

        return false;
    }

    public boolean deleteUser(int userId) {

        NodeUser existingUser = findUserById(userId);

        if (existingUser != null) {
            modelUser.deleteUser(userId);
            return true;
        }

        return false;
    }

    public NodeUser findUserById(int userId) {
        List<NodeUser> userList = modelUser.getUserList();

        if (userList != null) {
            for (NodeUser user : userList) {
                if (user.getId_user() == userId) {
                    return user;
                }
            }
        }

        return null;
    }

    public static NodeUser findUserById(int userId, ArrayList<NodeUser> userList) {
        if (userList != null) {
            for (NodeUser user : userList) {
                if (user.getId_user() == userId) {
                    return user;
                }
            }
        }

        return null;
    }

    public int authenticateUser(String username, String password){
        int idx = -1;
        for (int i = 0; i < modelUser.getUserList().size(); i++) {
            if (modelUser.getUserList().get(i).getUsername().equals(username)
                    && modelUser.getUserList().get(i).getPassword().equals(password)){
                idx = i;
            }
        }

        return idx;
    }
}
