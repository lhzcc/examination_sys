package com.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.system.exception.CustomException;
import com.system.po.College;
import com.system.po.CourseCustom;
import com.system.po.PagingVO;
import com.system.po.StudentCustom;
import com.system.po.TeacherCustom;
import com.system.po.Userlogin;
import com.system.service.CollegeService;
import com.system.service.CourseService;
import com.system.service.StudentService;
import com.system.service.TeacherService;
import com.system.service.UserloginService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource(name = "studentServiceImpl")
    private StudentService studentService;

    @Resource(name = "teacherServiceImpl")
    private TeacherService teacherService;

    @Resource(name = "courseServiceImpl")
    private CourseService courseService;

    @Resource(name = "collegeServiceImpl")
    private CollegeService collegeService;

    @Resource(name = "userloginServiceImpl")
    private UserloginService userloginService;

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<瀛︾敓鎿嶄綔>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    //  瀛︾敓淇℃伅鏄剧ず
    @RequestMapping("/showStudent")
    public String showStudent(Model model, Integer page) throws Exception {

        List<StudentCustom> list = null;
        //椤电爜瀵硅薄
        PagingVO pagingVO = new PagingVO();
        //璁剧疆鎬婚〉鏁�
        pagingVO.setTotalCount(studentService.getCountStudent());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = studentService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = studentService.findByPaging(page);
        }

        model.addAttribute("studentList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showStudent";

    }

    //  娣诲姞瀛︾敓淇℃伅椤甸潰鏄剧ず
    @RequestMapping(value = "/addStudent", method = {RequestMethod.GET})
    public String addStudentUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);

        return "admin/addStudent";
    }

     // 娣诲姞瀛︾敓淇℃伅鎿嶄綔
    @RequestMapping(value = "/addStudent", method = {RequestMethod.POST})
    public String addStudent(StudentCustom studentCustom, Model model) throws Exception {

        Boolean result = studentService.save(studentCustom);

        if (!result) {
            model.addAttribute("message", "瀛﹀彿閲嶅");
            return "error";
        }
        //娣诲姞鎴愬姛鍚庯紝涔熸坊鍔犲埌鐧诲綍琛�
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(studentCustom.getUserid().toString());
        userlogin.setPassword("123");
        userlogin.setRole(2);
        userloginService.save(userlogin);

        //閲嶅畾鍚�
        return "redirect:/admin/showStudent";
    }

    // 淇敼瀛︾敓淇℃伅椤甸潰鏄剧ず
    @RequestMapping(value = "/editStudent", method = {RequestMethod.GET})
    public String editStudentUI(Integer id, Model model) throws Exception {
        if (id == null) {
            //鍔犲叆娌℃湁甯﹀鐢焛d灏辫繘鏉ョ殑璇濆氨杩斿洖瀛︾敓鏄剧ず椤甸潰
            return "redirect:/admin/showStudent";
        }
        StudentCustom studentCustom = studentService.findById(id);
        if (studentCustom == null) {
            throw new CustomException("鏈壘鍒拌鍚嶅鐢�");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("student", studentCustom);


        return "admin/editStudent";
    }

    // 淇敼瀛︾敓淇℃伅澶勭悊
    @RequestMapping(value = "/editStudent", method = {RequestMethod.POST})
    public String editStudent(StudentCustom studentCustom) throws Exception {

        studentService.updataById(studentCustom.getUserid(), studentCustom);

        //閲嶅畾鍚�
        return "redirect:/admin/showStudent";
    }

    // 鍒犻櫎瀛︾敓
    @RequestMapping(value = "/removeStudent", method = {RequestMethod.GET} )
    private String removeStudent(Integer id) throws Exception {
        if (id == null) {
            //鍔犲叆娌℃湁甯﹀鐢焛d灏辫繘鏉ョ殑璇濆氨杩斿洖瀛︾敓鏄剧ず椤甸潰
            return "admin/showStudent";
        }
        studentService.removeById(id);
        userloginService.removeByName(id.toString());

        return "redirect:/admin/showStudent";
    }

    // 鎼滅储瀛︾敓
    @RequestMapping(value = "selectStudent", method = {RequestMethod.POST})
    private String selectStudent(String findByName, Model model) throws Exception {

        List<StudentCustom> list = studentService.findByName(findByName);

        model.addAttribute("studentList", list);
        return "admin/showStudent";
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<鏁欏笀鎿嶄綔>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 鏁欏笀椤甸潰鏄剧ず
    @RequestMapping("/showTeacher")
    public String showTeacher(Model model, Integer page) throws Exception {

        List<TeacherCustom> list = null;
        //椤电爜瀵硅薄
        PagingVO pagingVO = new PagingVO();
        //璁剧疆鎬婚〉鏁�
        pagingVO.setTotalCount(teacherService.getCountTeacher());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = teacherService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = teacherService.findByPaging(page);
        }

        model.addAttribute("teacherList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showTeacher";

    }

    // 娣诲姞鏁欏笀淇℃伅
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.GET})
    public String addTeacherUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);

        return "admin/addTeacher";
    }

    // 娣诲姞鏁欏笀淇℃伅澶勭悊
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.POST})
    public String addTeacher(TeacherCustom teacherCustom, Model model) throws Exception {

        Boolean result = teacherService.save(teacherCustom);

        if (!result) {
            model.addAttribute("message", "宸ュ彿閲嶅");
            return "error";
        }
        //娣诲姞鎴愬姛鍚庯紝涔熸坊鍔犲埌鐧诲綍琛�
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(teacherCustom.getUserid().toString());
        userlogin.setPassword("123");
        userlogin.setRole(1);
        userloginService.save(userlogin);

        //閲嶅畾鍚�
        return "redirect:/admin/showTeacher";
    }

    // 淇敼鏁欏笀淇℃伅椤甸潰鏄剧ず
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.GET})
    public String editTeacherUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showTeacher";
        }
        TeacherCustom teacherCustom = teacherService.findById(id);
        if (teacherCustom == null) {
            throw new CustomException("鏈壘鍒拌鍚嶅鐢�");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("teacher", teacherCustom);


        return "admin/editTeacher";
    }

    // 淇敼鏁欏笀淇℃伅椤甸潰澶勭悊
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.POST})
    public String editTeacher(TeacherCustom teacherCustom) throws Exception {

        teacherService.updateById(teacherCustom.getUserid(), teacherCustom);

        //閲嶅畾鍚�
        return "redirect:/admin/showTeacher";
    }

    //鍒犻櫎鏁欏笀
    @RequestMapping("/removeTeacher")
    public String removeTeacher(Integer id) throws Exception {
        if (id == null) {
            //鍔犲叆娌℃湁甯︽暀甯坕d灏辫繘鏉ョ殑璇濆氨杩斿洖鏁欏笀鏄剧ず椤甸潰
            return "admin/showTeacher";
        }
        teacherService.removeById(id);
        userloginService.removeByName(id.toString());

        return "redirect:/admin/showTeacher";
    }

    //鎼滅储鏁欏笀
    @RequestMapping(value = "selectTeacher", method = {RequestMethod.POST})
    private String selectTeacher(String findByName, Model model) throws Exception {

        List<TeacherCustom> list = teacherService.findByName(findByName);

        model.addAttribute("teacherList", list);
        return "admin/showTeacher";
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<璇剧▼鎿嶄綔>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 璇剧▼淇℃伅鏄剧ず
    @RequestMapping("/showCourse")
    public String showCourse(Model model, Integer page) throws Exception {

        List<CourseCustom> list = null;
        //椤电爜瀵硅薄
        PagingVO pagingVO = new PagingVO();
        //璁剧疆鎬婚〉鏁�
        pagingVO.setTotalCount(courseService.getCountCouse());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = courseService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = courseService.findByPaging(page);
        }

        model.addAttribute("courseList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showCourse";

    }

    //娣诲姞璇剧▼
    @RequestMapping(value = "/addCourse", method = {RequestMethod.GET})
    public String addCourseUI(Model model) throws Exception {

        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();

        model.addAttribute("collegeList", collegeList);
        model.addAttribute("teacherList", list);

        return "admin/addCourse";
    }

    // 娣诲姞璇剧▼淇℃伅澶勭悊
    @RequestMapping(value = "/addCourse", method = {RequestMethod.POST})
    public String addCourse(CourseCustom courseCustom, Model model) throws Exception {

        Boolean result = courseService.save(courseCustom);

        if (!result) {
            model.addAttribute("message", "璇剧▼鍙烽噸澶�");
            return "error";
        }


        //閲嶅畾鍚�
        return "redirect:/admin/showCourse";
    }

    // 淇敼鏁欏笀淇℃伅椤甸潰鏄剧ず
    @RequestMapping(value = "/editCourse", method = {RequestMethod.GET})
    public String editCourseUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showCourse";
        }
        CourseCustom courseCustom = courseService.findById(id);
        if (courseCustom == null) {
            throw new CustomException("鏈壘鍒拌璇剧▼");
        }
        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();

        model.addAttribute("teacherList", list);
        model.addAttribute("collegeList", collegeList);
        model.addAttribute("course", courseCustom);


        return "admin/editCourse";
    }

    // 淇敼鏁欏笀淇℃伅椤甸潰澶勭悊
    @RequestMapping(value = "/editCourse", method = {RequestMethod.POST})
    public String editCourse(CourseCustom courseCustom) throws Exception {

        courseService.upadteById(courseCustom.getCourseid(), courseCustom);

        //閲嶅畾鍚�
        return "redirect:/admin/showCourse";
    }

    // 鍒犻櫎璇剧▼淇℃伅
    @RequestMapping("/removeCourse")
    public String removeCourse(Integer id) throws Exception {
        if (id == null) {
            //鍔犲叆娌℃湁甯︽暀甯坕d灏辫繘鏉ョ殑璇濆氨杩斿洖鏁欏笀鏄剧ず椤甸潰
            return "admin/showCourse";
        }
        courseService.removeById(id);

        return "redirect:/admin/showCourse";
    }

    //鎼滅储璇剧▼
    @RequestMapping(value = "selectCourse", method = {RequestMethod.POST})
    private String selectCourse(String findByName, Model model) throws Exception {

        List<CourseCustom> list = courseService.findByName(findByName);

        model.addAttribute("courseList", list);
        return "admin/showCourse";
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<鍏朵粬鎿嶄綔>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 鏅�氱敤鎴疯处鍙峰瘑鐮侀噸缃�
    @RequestMapping("/userPasswordRest")
    public String userPasswordRestUI() throws Exception {
        return "admin/userPasswordRest";
    }

    // 鏅�氱敤鎴疯处鍙峰瘑鐮侀噸缃鐞�
    @RequestMapping(value = "/userPasswordRest", method = {RequestMethod.POST})
    public String userPasswordRest(Userlogin userlogin) throws Exception {

        Userlogin u = userloginService.findByName(userlogin.getUsername());

        if (u != null) {
            if (u.getRole() == 0) {
                throw new CustomException("璇ヨ处鎴蜂负绠＄悊鍛樿处鎴凤紝娌℃硶淇敼");
            }
            u.setPassword(userlogin.getPassword());
            userloginService.updateByName(userlogin.getUsername(), u);
        } else {
            throw new CustomException("娌℃壘鍒拌鐢ㄦ埛");
        }

        return "admin/userPasswordRest";
    }

    // 鏈处鎴峰瘑鐮侀噸缃�
    @RequestMapping("/passwordRest")
    public String passwordRestUI() throws Exception {
        return "admin/passwordRest";
    }


}
