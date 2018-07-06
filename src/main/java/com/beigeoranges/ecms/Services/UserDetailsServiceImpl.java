package com.beigeoranges.ecms.Services;
        import java.util.ArrayList;
        import java.util.List;

        import com.beigeoranges.ecms.Dao.RoleDao;
        import com.beigeoranges.ecms.Dao.UserDao;
        import com.beigeoranges.ecms.Model.User;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User User = this.userDao.findUserAccount(userName);

        if (User == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + User);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.roleDao.getRoleNames(User.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(User.getEmail(),User.getEncryptedPassword(), grantList);
        return userDetails;
    }

}