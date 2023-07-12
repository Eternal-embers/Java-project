                                            多项式计算器——Java实现


Element类
用Element类表示多项式的中的一项，它的系数和次方都是用double类型，因此即使是次方出现小数同样也能计算。



Polynomial类
用Polynomial类表示一个多项式，每一个Polynimial对象有一个成员为Element对象的ArrayList，将多项式的每一项存放在ArrayList中，ArrayList可以实现多项式的项数不受限制。

多项式中一项的添加是通过插入法实现，为了让多项式按每一项的次方大小来排序，每一次向ArrayList中添加Element对象都要找到合适的位置再插入。

多项式的每一项的次方可能出现小数，因此下面的printDouble实现根据指定精确到小数点后几位来输出小数。

在Polynomial还提供打印多项式的方法，打印多项式的形式像这样：[X^5] + 5[X^4] + 10[X^3] + 10[X^2] + 5[X^1] + 1



Digit接口
Digit实现获取整数的位数和小数的位数



Result类
Result类用于存储多项式的计算结果，在计算中每一次的计算结果可以通过Result类来管理，该类提供了一个方法打印所有保存的计算结果。



PolynomialOperation类(核心）
实现多项式的加减乘以及幂运算，通过下面的add()、sub()、mult()、pow()四个方法实现

add(polynomial poly1,polynomial poly2)方法实现多项式加法，Polynomial类中有一个add()方法可以将多项式的项Element对象添加到一个Polynomial对象中，对于两个多项式Poly1和Poly2，要计算poly1+poly2，假设poly1的项数大于等于poly2，那么我们为了不影响poly1对象，我们深复制一份poly1对象，这里选取项数多的对象poly1主要是为了减少计算步骤，在将poly2中的所有项添加到复制的poly1对象中后，复制的poly1对象就是计算结果。

sub(polynomial poly1,polynomial poly2)实现多项式减法，sub()方法与add()极其类似，假设有poly1、poly2两个多项式对象，对于poly1-poly2，只需要将poly2中的每一项的系数取相反数，再将每一项添加到poly1中，最终得到的poly1就是计算结果，不过为了不影响原先的poly1对象，我们需要深复制一个poly1对象用于运算。

mult(polynomial poly1,polynomial poly2)方法实现两个多项式的乘法，假设有poly1、poly2两个多项式对象，res多项式对象存储计算结果，对于poly1*poly2，我们将poly1中的每一项与poly2中的每一项相乘的结果添加到res多项式对象中，两项相乘只需要将系数相乘，指数相加，根据得到的系数和指数创建对应的Element对象并添加到res多项式对象中，因此只需要两个for循环即可实现多项式乘法。

pow(polynomial poly,int power)方法实现多项式的幂运算，幂运算我们用递归来实现。

假设要计算多项式poly的n次幂：

1.n为偶数，计算poly的n次幂，那么我们可以先计算poly的n/2次幂的结果，再将结果相乘；要计算poly的n/2次幂，我们可以先计算poly的n/4次幂，再将结果相乘......；最终可以递归到只需要计算poly的平方调用方法mult(poly,poly)可以实现。

2.n为奇数，计算poly的n次幂，假设n = 2k + 1，那么我们可以知道2k为偶数，那么我们可以先计算poly的2k次幂，这可以通过上面的n为偶数的情况来实现，只需要调用方法pow(poly,n/2)即可得到poly的2k次幂，再将这个结果与poly相乘就得到poly的n次幂结果，这也可以用递归来实现。



PolynomialCalculator类
这个类实现多项式计算器，用户的与程序的交互操作在里面实现，它提供了一系列的方法增加使用体验。

计算器中可以存储两个多项式poly1和poly2用于计算，这两个多项式可以通过输入获得，或者从计算结果中导入，每一次的多项式输入会覆盖这两个多项式。

计算中可以保存计算结果，同时可以查看所有计算结果，清空计算结果可以释放程序的空间但同时将丢失计算结果。

还可以给对象式中的变量x赋值求得多项式的赋值结果。
