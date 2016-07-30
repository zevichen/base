package com.czw.jichu.threadeducation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


//不能改动此Test类	
public class Test3 extends Thread{
	
	private PCDo pcDo;
	private String key;
	private String value;
	
	public Test3(String key,String key2,String value){
		this.pcDo = PCDo.getInstance();
		/*常量"1"和"1"是同一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
		以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个的效果*/
		this.key = key+key2; 
/*		a = "1"+"";
		b = "1"+""
*/
		this.value = value;
	}


	public static void main(String[] args) throws InterruptedException{
		Test3 a = new Test3("1","","1");
		Test3 b = new Test3("1","","2");
		Test3 c = new Test3("3","","3");
		Test3 d = new Test3("4","","4");
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		a.start();
		b.start();
		c.start();
		d.start();

	}
	
	public void run(){
		pcDo.doSome(key, value);
	}
}

class PCDo {

	private PCDo() {}
	private static PCDo _instance = new PCDo();	
	public static PCDo getInstance() {
		return _instance;
	}

	//private ArrayList keys = new ArrayList(); 使用这个集合会出现迭代中不能修改数据的异常。
	private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();
	public void doSome(Object key, String value) {
		Object o = key;
		if(!keys.contains(o)){
			keys.add(o);
		}else{

			for(Iterator iter=keys.iterator();iter.hasNext();){
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Object oo = iter.next();
				if(oo.equals(o)){
					o = oo;
					break;
				}
			}
		}
		synchronized(o)
		// 以大括号内的是需要局部同步的代码，不能改动!
		{
			try {
				Thread.sleep(1000);
				System.out.println(key+":"+value + ":"
						+ (System.currentTimeMillis() / 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
