package com.practice.all.demo.controll;

import com.practice.all.demo.Entity.Student;
import com.practice.all.demo.configure.JedisUtil;
import com.practice.all.demo.configure.Reciver;
import com.practice.all.demo.configure.Send;
import com.practice.all.demo.dao.UserDao;
import com.practice.all.demo.service.UserService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Controller
public class StudentControll {

    @Autowired
    private JedisUtil jedisUtil;

   /* @Autowired
    private UserDao userDao;*/

    @Autowired
    private UserService userService;

    @Autowired
    private Send send;

    @Autowired
    private Reciver reciver;

    @RequestMapping("/addStudent")
    public String addStudent(HttpServletRequest request, Model model) throws IOException, TimeoutException {
        String studentId = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String name = jedisUtil.queryJedis(studentId);

        if(name == null){
            //redis 为空，到elasticsearch找
            Student student = userService.findById(studentId);
            if(student != null && student.getName() != null){
                model.addAttribute("msg","已经有该学生了。。。");
            }else {
                send.addMQ(studentId,studentName);
                model.addAttribute("msg","增加成功!!!!！！！！");
            }
        }else{
            model.addAttribute("msg","已经有该学生了。。。");
        }

        return "index";
    }

    @RequestMapping("/amendStu")
    public String amendStu(HttpServletRequest request, Model model) throws IOException, TimeoutException {
        String studentId = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String name = jedisUtil.queryJedis(studentId);

        if(name == null){
            //redis 为空，到elasticsearch找
            //Optional<Student> optional = userService.findById(studentId);
            Student student = userService.findById(studentId);
            if(student != null && student.getName() != null){
                //修改成功,redis 要不要改变
                send.amendMQ(studentId,studentName);
                model.addAttribute("msg","修改学生成功");
            }else {
                model.addAttribute("msg","现在暂时还没有该学生！！！！");
            }
        }else{
            send.amendMQ(studentId,studentName);
            jedisUtil.delJedis(studentId);
            model.addAttribute("msg","修改学生成功");
        }
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/qidong")
    public String qidong() throws IOException {
        reciver.getReciver();
        return "index";
    }


    @RequestMapping("/queryStu")
    @ResponseBody
    public String queryStu(HttpServletRequest request, Model model) throws IOException {
        String stuId = request.getParameter("studentId");
        String name = jedisUtil.queryJedis(stuId);
        if(name == null){
            Student student = userService.findById(stuId);
            if(student != null && student.getName() != null){
                System.out.println(student.getName());
                jedisUtil.setJedis(student.getId(),student.getName());
                return "这个学生是："+student;
            }else {
                return "现在暂时还没有该学生！！！！";
            }
        }
        //reciver.getReciver();
        return "这个学生是： 学号为："+stuId+"  姓名为："+name;
    }

    @RequestMapping("/delStu")
    public String delStu(HttpServletRequest request, Model model) throws IOException, TimeoutException {
        String studentId = request.getParameter("studentId");
        String name = jedisUtil.queryJedis(studentId);

        if(name == null){
            //redis 为空，到elasticsearch找
            Student student = userService.findById(studentId);
            if(student != null && student.getName() != null){
                //修改成功,redis 要不要改变
                send.delMQ(studentId);
                userService.deleteById(studentId);
                model.addAttribute("msg","删除学生成功");
            }else {
                model.addAttribute("msg","现在暂时还没有该学生！！！！");
            }
        }else{
            //发送消息到rabbitMQ
            send.delMQ(studentId);
            //es删除该索引
            userService.deleteById(studentId);
            //redis删除该索引
            jedisUtil.delJedis(studentId);
            model.addAttribute("msg","删除学生成功");
        }
        return "index";
    }

    @RequestMapping("findTestLike")
    @ResponseBody
    public List<Student> findTestLike(HttpServletRequest request) {
        String name = request.getParameter("studentName");
        QueryBuilder qb1 = QueryBuilders.wildcardQuery("name", "*"+name+"*".toLowerCase());
        //QueryBuilder qb1 =  QueryBuilders.queryStringQuery(name).field("name");
        List<Student> list = userService.search(qb1);
        return list;
    }

    /*//添加文档
    @RequestMapping("/addUser")
    @ResponseBody
    public Student addUser(@RequestBody Student userEntity) {
        return userDao.save(userEntity);
    }*/

    @RequestMapping("/get")
    @ResponseBody
    public String getAll() {
        List<Student> list = new ArrayList<Student>();
        list = userService.findAll();
       /* userService.findAll().forEach(stu -> {
            System.out.println("stuId: "+stu.getId()+"   stuName: "+stu.getName());
            Student student = new Student();
            student = stu;
            list.add(student);
        });*/
        return "所有的学生为："+list;
    }


}
