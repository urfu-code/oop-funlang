# Fun lang

Простой функциональный язык программирования, разработанный как основа для практических задач в рамках курса объектно-ориентированного программирования в УрФУ.

## Как начать работать над fun?

### С чего начать знакомство с проектом?

Начать стоит с изучения спецификации языка, она приведена ниже.

Чтобы получить исходники, ыы можете склонировать этот репозиторий, если уже знакомы с системой контроля версий git.
Либо можете скачать все исходники, воспользовавшись кнопкой Download ZIP.

После этого можно запустить пакет с [REPL-консолью](http://ru.wikipedia.org/wiki/REPL) и поизучать язык экспериментально.

Затем изучить тесты и запустить их.

После этого можно начинать изучать код например, начиная с REPL или с тестов.


### Как сдавать решения задач?

Если вы сдаете задачу по почте, то в качестве решения принимается zip-архив с директорией src.

Постарайтесь менять только те файлы, которые необходимо изменить для решения задачи. В противном случае, если, например, вы решите переформатировать все исходники на свой лад, задачу будет сложно проверять и преподаватели оставляют за собой право не принимать подобные решения.

В сдаваемом вами решении не должно быть предупреждений, выдаваемых средой разработки.


## Спецификация языка fun
    
### Грамматика
    
```
Program ::= Statement* 
Statement ::= 'let' NAME ':=' Term ';'
Statement ::= Term ';'
Term ::= NAME
Term ::= Term Term
Term ::= '(' Term ')'
Term ::= 'fun' '(' name ')' Term
```    
    
### Пояснения

Программа на языке Fun — это произвольная последовательность из определений 
и вызовов встроенных или определенных ранее функций.

Ниже перечисляются все правила грамматики fun с некоторыми комментариями.

Программа — это последовательность предложений.
```
1. Program ::= Statement* 
```
Предложения бывают двух видов — определение и вызов функции. 
Все предложения оканчиваются точкой с запятой.

```
2. Statement ::= 'let' NAME ':=' Term ';'
3. Statement ::= Term ';'
```

Определение дает имя выражению, стоящему справа от знака ':=', предварительно 
вычисляя это выражение, если это возможно. 
Далее это имя может использоваться для обозначения этого результата вычисления этого выражения.

```
let print1 := print 1;
```

Код выше тут же напечатает единичку (это результат вычисления функции print). 
А последующие вызовы print1 ничего уже печатать не будут.

В качестве Term может использоваться имя встроенной или определенной 
ранее функции (в fun все является функцией, даже константы — это тоже функции):

```
4. Term ::= NAME
```

Либо вызов функции. В fun вызов функции — это самая распространенная операция, 
поэтому чтобы сделать синтаксис более лаконичным, скобки, окружающие список аргументов писать не нужно.

```
5. Term ::= Term Term
```

Например, "sin x" в языке fun — это корректный вызов функции sin с аргументом x.

Для целей группировки, выражения можно заключать в круглые скобки.

```
6. Term ::= '(' Term ')'
```

И, наконец, выражение может определять новую анонимную функцию.

```
7. Term ::= 'fun' '(' name ')' Term
```

Анонимность означает, что у функции нет имени. 
Однако ее по прежнему можно вызывать и вообще делать с ней все, что захочется. Вот пара примеров:

```
doSomething (fun(x) + x x); // Передали анонимную функцию в качестве аргумента
(fun(x) + x x) 2; // Передали анонимной функции в качестве аргумента 2. 
```

В результате вычисления этого выражения получится 4.
Как видно, язык fun лишён возможности инфиксной записи арифметических выражений. 
Вместо этого все арифметические операции представлены в виде функций.
Так, '+', '=', '<' — это имена встроенных функций.

Ещё одно замечания — в языке fun есть функции только от одной переменной. 
Это не является серьезным ограничением, поскольку функция от нескольких переменных 
легко моделируется с помощью функций от одной переменной вот так:

```
f(x, y) = (f(x))(y)
```

если положить, что f(x) возвращает функцию, которую можно применить ко аргументу "y".

Несколько примеров определения функций:

```
let id := fun(x) x;

let inc := fun(x) + x 1;

let sum := fun(a)fun(b)fun(c) + a (+ b c);
```


### Ленивые вычисления

В языке есть возможность вычислять аргументы функции лениво, то есть только если он понадобился.
Для этого имя ленивого аргумента функции должно начинаться с тильды:

```
let true := fun (~t) fun (~f) ~t;
let false := fun (~t) fun (~f) ~f;

true (print 1) (print 2);
```

Код выше напечатает только единичку: значение второго аргумента не используется, 
а значит он не будет вычисляться. С помощью этой возможности, считая, что true и false определены так, 
как приведено выше, можно определить оператор if then else как функцию:

```
let if := fun(~cond) fun(~then) fun(~else) ~cond ~then ~else;

if false (print 1) 42;
```

вернет 42 и ничего не напечатает.


### Типы данных

На данный момент из типов данных доступны лишь два: Bool и Int.
Bool может принимать всего два значения true и false, определенные так, как показано выше.


### Встроенные функции
На данный момент доступны следующие функции: 

(+ a b) сложение a и b

(= a b) сравнение на равенство a и b

(print x) печать на консоль своего аргумента x  


  
### Рекурсивные функции
Язык fun предоставляет возможность создавать рекурсивные функции:

```
let fibb := fun(n)
        (< n 2) 
                1
                (+ (fibb (-n 1)) (fibb (- n 2)))
        );

let fact := fun(n) 
        ((= n 1) 
                1 
                (* (fact (-n 1)) n)
        );
```

На самом деле любой цикл можно преобразовать в вызовы рекурсивных функций. 
Поэтому язык может обходиться без явных операторов цикла, хотя это и не всегда удобно.
