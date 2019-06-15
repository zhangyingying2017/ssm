package cn.edu.zzuli.annotatior;



import java.lang.annotation.*;

/**
 * Created by lee on 15/9/14.
 *
 *  注意：同一个类如下调用，调用b方法，c方法是不能记录日志
 @ActionLog
 public void a(){}
 @ActionLog
 public void b(){c();}
 @ActionLog
 public void c(){}
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActionLog {
}
