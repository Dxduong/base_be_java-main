package com.example.novel_app.constant;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum SuccessMessage {
    // AUTHEN , AUTHORIZE
    LOGIN("Đăng nhập"),
    REGISTER("Đăng kí"),
    UPDATE_PASS("Cập nhật mật khẩu"),
    UPDATE_INFOR("Cập nhật thông tin"),
    // AUTHOR
    GET_ALL_AUTHOR("Lấy tất cả tác giả"),
    CREATE_AUTHOR("Tạo tác giả"),
    UPDATE_AUTHOR("Cập nhật thông tác giả"),
    DELETE_AUTHOR("Xóa tác giả"),
    TAKE_AUTHOR("Lấy thông tin tác giả"),
    SEARCH_AUTHOR_BY_NAME("Tìm kiếm tác giả"),
    // NOVEL
    GET_ALL_NOVEL("Lấy tất cả tiểu thuyết"),
    CREATE_NOVEL("Tạo tiểu thuyết"),
    UPDATE_NOVEL("Cập nhật thông tin tiểu thuyết"),
    DELETE_NOVEL("Xóa tác giả"),
    TAKE_NOVEL("Lấy thông tin tiểu thuyết"),
    SEARCH_NOVEL_BY_NAME("Tìm kiếm tác giả"),

    // CHAPTER
    GET_ALL_CHAPTER_BY_NOVEL("Lấy tất cả chương của tiểu thuyết"),
    CREATE_CHAPTER("Tạo chương tiểu thuyết"),
    UPDATE_CHAPTER("Cập nhật chương tiểu thuyế"),
    DELETE_CHAPTER("Xóa tác chương tiểu thuyết"),
    TAKE_CHAPTER("Lấy chương tiểu thuyết"),
    // GENRE
    GET_ALL_GENRE("Lấy tất cả các thể loại"),
    CREATE_GENRE("Tạo thể loại"),
    UPDATE_GENRE("Cập nhật thông tin thể loại"),
    DELETE_GENRE("Xóa tác thể loại"),
    TAKE_GENRE("Lấy thông tin thể loại"),
    ;
    public String getMessage() {
        return message + " thành công";
    }
    String message;
}
