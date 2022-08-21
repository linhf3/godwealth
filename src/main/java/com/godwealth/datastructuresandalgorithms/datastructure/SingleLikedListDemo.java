package com.godwealth.datastructuresandalgorithms.datastructure;

public class SingleLikedListDemo {
    public static void main(String[] args) {
        //先创建节点
        HeroNode heroNode1 = new HeroNode(1, "x", "x");
        HeroNode heroNode2 = new HeroNode(2, "xx", "xx");
        HeroNode heroNode3 = new HeroNode(3, "xxx", "xxx");
        HeroNode heroNode4 = new HeroNode(4, "xxxx", "xxxx");
        HeroNode heroNode5 = new HeroNode(5, "xxxxx", "xxxxx");
        HeroNode newheroNode = new HeroNode(2, "xx1", "xx1");
        //创建一个链表
        SingleLikedlist singleLikedlist = new SingleLikedlist();
        singleLikedlist.addOder(heroNode1);
        singleLikedlist.addOder(heroNode2);
        singleLikedlist.addOder(heroNode3);
        singleLikedlist.addOder(heroNode5);
        singleLikedlist.addOder(heroNode4);
        singleLikedlist.showList();
        System.out.println("修改后");
        singleLikedlist.updata(newheroNode);
        singleLikedlist.showList();
        singleLikedlist.deleteNode(1);
        singleLikedlist.deleteNode(5);
        System.out.println("删除后");
        singleLikedlist.showList();
    }
}

//定义SingleLinkedlist 管理我们的英雄
class SingleLikedlist {
    //先初始化一个头节点
    private HeroNode head = new HeroNode(0, "", "");

    //添加节点到单向节点
    //当不考虑编号的顺序时，找到当前链表的最后节点，将最后这个节点测next指向新节点
    public void add(HeroNode heroNode) {
        //因为head节点不能动，因此我们需要一个辅助变量
        HeroNode temp = head;
        //遍历链表，找到最后
        while (true) {
            if (temp.next == null) {
                break;
            }
            //没有到最后
            temp = temp.next;
        }
        //当推出while循环时，temp指向链表最后
        //将最后这个节点的next指向新的节点
        temp.next = heroNode;//将最后一个节点的next指向新加入的点
    }

    //按顺序添加
    public void addOder(HeroNode heroNode) {
        //因为head节点不能动，因此我们需要一个辅助变量,找到添加的位置
        //找的temp是添加位置的前一个节点
        HeroNode temp = head;
        boolean flag = false;//添加的编号是否存在
        while (true) {
            if (temp.next == null) {//说明temp已经在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) {///位置找到了，在temp后面添加
                break;
            } else if (temp.next.no == heroNode.no) {//编号已经存在
                flag = true;
                break;
            }
            temp = temp.next;//后移继续寻找
        }
        //判断flag值
        if (flag) {
            System.out.println("编号已经存在" + heroNode.no);
        } else {//可以插入到temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;

        }

    }

    //修改
    //根据no信息来进行修改
    public void updata(HeroNode heroNode) {
        if (head.next == null) {
            System.out.println("链表为空");
        }
        //找到需要修改的节点
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;//链表已经遍历结束了
            }
            if (temp.no == heroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = heroNode.name;
            temp.nickname = heroNode.nickname;
        } else {
            System.out.println("没有找到这个编号" + heroNode.no);
        }
    }

    //删除
    public void deleteNode(int no) {
        if (head.next == null) {
            return;
        }
        boolean flag = false;
        HeroNode temp = head;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;

        }
    }

    //显示链表
    public void showList() {
        //先判断链表是否为空
        if (head.next == null) {
            return;
        }
        HeroNode temp = head.next;
        while (true) {
            //是否到链表最后
            if (temp == null) {
                break;
            }
            //输出节点信息；
            System.out.println(temp);
            temp = temp.next;//后移节点
        }


    }

}

//定义一个HeroNode，每一个HroeNode就是一个节点
class HeroNode {
    //定义节点的私有属性 编号，姓名 外号 和next
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;
    //构造器

    public HeroNode() {
    }

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //重写toString 方便显示
    public String toString() {
        return "Heronode [no= " + no + ",name=" + name + ",nickname=" + nickname + "]";
    }
}
