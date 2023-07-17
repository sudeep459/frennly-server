package com.frennly.ds.payload.response;


import com.frennly.ds.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

    private Integer id;

    private String username;

    private String email;

    private UserType userType;

    private String profileImage;

    private String preferredTimings;
}
