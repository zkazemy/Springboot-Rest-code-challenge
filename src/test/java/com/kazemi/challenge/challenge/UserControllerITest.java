package com.kazemi.challenge.challenge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazemi.challenge.challenge.dto.*;
import com.kazemi.challenge.challenge.model.User;
import com.kazemi.challenge.challenge.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class UserControllerITest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper json;
    @Autowired
    protected UserRepository userRepository;

    @Test
    @DisplayName("find a user by user id without throwing any exception")
    void findUserById_successfully() throws Exception {

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/findById/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<UserResponseDto> response = json.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<UserResponseDto>>() {
                });

        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.getResponse().getFirstName()).isEqualTo("reza");
        assertThat(response.getResponse().getLastName()).isEqualTo("akbari");
    }

    @Test
    @DisplayName("find a user by user name without throwing any exception")
    void findUserByName_successfully() throws Exception {

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/findByName/{name}", "reza")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<List<UserResponseDto>> response = json.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<List<UserResponseDto>>>() {
                });

        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.getResponse().size()).isEqualTo(2);

        assertThat(response.getResponse().get(0).getFirstName()).isEqualTo("reza");
        assertThat(response.getResponse().get(0).getLastName()).isEqualTo("akbari");

        assertThat(response.getResponse().get(1).getFirstName()).isEqualTo("reza");
        assertThat(response.getResponse().get(1).getLastName()).isEqualTo("morasli");

    }

    @Test
    @DisplayName("delete a user by user id without throwing any exception")
    void deleteUserById_successfully() throws Exception {

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + "/delete/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<Void> response = json.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<Void>>() {
                });

        User deletedUser = userRepository.findById(3L).orElse(null);

        assertThat(response.getResponse()).isNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(deletedUser).isNull();
    }

    @Test
    @DisplayName("create a new user with invalid information that will be thrown an exception")
    void createNewUser_DataInvalid_ExceptionThrown() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + "/new")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());

        BaseResponseDto<UserResponseDto> response = json.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<UserResponseDto>>() {
                });

        assertThat(response.getResponse()).isNull();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.getErrorData().getErrorCode()).isEqualTo(1001);

    }

    @Test
    @DisplayName("create a new user with valid information without throwing any exception ")
    void createNewUser_Successfully() throws Exception {
        UserRequestDto requestDto = UserRequestDto.builder()
                .firstName("Sarah")
                .lastName("Rasoom")
                .emails(getEmail())
                .phoneNumbers(getPhoneNumber())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + "/new")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<UserResponseDto> response = json.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<UserResponseDto>>() {
                });

        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();

        assertThat(response.getResponse().getFirstName()).isEqualTo("Sarah");
        assertThat(response.getResponse().getLastName()).isEqualTo("Rasoom");

        assertThat(response.getResponse().getEmails().size()).isEqualTo(2);
        assertThat(response.getResponse().getPhoneNumbers().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("add new contact information without throwing any exception")
    void addContactInformation_Successfully() throws Exception {
        UserRequestDto requestDto = UserRequestDto.builder()
                .id(1L)
                .emails(Stream.of(EmailDto.builder().mail("zahraKazemi.mi@gmail.com").build()).collect(Collectors.toSet()))
                .phoneNumbers(Stream.of(PhoneNumberDto.builder().number("+9833223333").build()).collect(Collectors.toSet()))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + "/add-contact")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<Void> addContactResponse = json.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<Void>>() {
                });

        ResultActions findUserResultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/findById/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<UserResponseDto> findUserResponse = json.readValue(findUserResultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<UserResponseDto>>() {
                });

        assertThat(addContactResponse.getResponse()).isNull();
        assertThat(addContactResponse.isSuccessful()).isTrue();

        assertThat(findUserResponse.getResponse().getEmails().size()).isEqualTo(1);
        assertThat(findUserResponse.getResponse().getPhoneNumbers().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("update existing contact information without throwing any exception")
    void updateContactInformation_Successfully() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestDto requestDto = UserRequestDto.builder()
                .id(1L)
                .emails(Stream.of(EmailDto.builder().mail("zahraKazemi.mi@gmail.com").build()).collect(Collectors.toSet()))
                .phoneNumbers(Stream.of(PhoneNumberDto.builder().number("+9833223333").build()).collect(Collectors.toSet()))
                .build();

        ResultActions resultAddContactActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + "/add-contact")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<Void> addContactResponse = json.readValue(resultAddContactActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<Void>>() {
                });

        UserContactUpdateRequestDto userContactUpdateRequestDto = UserContactUpdateRequestDto.builder()
                .id(1L)
                .emails(Arrays.asList(new UserUpdateEmailRequestDto("zahraKazemi.mi@gmail.com","SaraKazemi@gmail.com")))
                .phoneNumbers(Arrays.asList(new UserUpdatePhoneNumberRequestDto("+9833223333","+9811111111")))
                .build();

        ResultActions updateContactActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + "/update-contact")
                .content(objectMapper.writeValueAsString(userContactUpdateRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<Void> updateContactResponse = json.readValue(updateContactActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<Void>>() {
                });

        ResultActions findUserResultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/findById/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        BaseResponseDto<UserResponseDto> findUserResponse = json.readValue(findUserResultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<UserResponseDto>>() {
                });

        assertThat(addContactResponse.getResponse()).isNull();
        assertThat(addContactResponse.isSuccessful()).isTrue();

        assertThat(updateContactResponse.getResponse()).isNull();
        assertThat(updateContactResponse.isSuccessful()).isTrue();

        assertThat(findUserResponse.getResponse().getEmails().size()).isEqualTo(1);
        assertThat(findUserResponse.getResponse().getPhoneNumbers().size()).isEqualTo(1);

        assertThat(findUserResponse.getResponse().getEmails().stream().findFirst().get().getMail()).isEqualTo("SaraKazemi@gmail.com");
        assertThat(findUserResponse.getResponse().getPhoneNumbers().stream().findFirst().get().getNumber()).isEqualTo("+9811111111");

    }

    private Set<EmailDto> getEmail() {

        EmailDto email1 = EmailDto.builder().mail("hello@gmail.com").build();
        EmailDto email2 = EmailDto.builder().mail("greeting@gmail.com").build();

        return Stream.of(email1, email2).collect(Collectors.toSet());

    }

    private Set<PhoneNumberDto> getPhoneNumber() {

        PhoneNumberDto phoneNumberDto1 = PhoneNumberDto.builder().number("+98343432434").build();
        PhoneNumberDto phoneNumberDto2 = PhoneNumberDto.builder().number("+9802002333").build();
        return Stream.of(phoneNumberDto1, phoneNumberDto2).collect(Collectors.toSet());

    }

}
