# Exemple_RecyclerView
Этот проект сделан для понимания как работает RecyclerView.

Компонент RecyclerView появился в Android 5.0 Lollipop и находится в разделе Containers. Для простоты буду называть его списком, хотя на самом деле это универсальный элемент управления с большими возможностями.

Раньше для отображения прокручиваемого списка использовался ListView. Со временем у него обнаружилось множество недостатков, которые было трудно исправить. Тогда решили создать новый элемент с нуля.

Вначале это был сырой продукт, потом его доработали. На данном этапе можно считать, что он стал полноценной заменой устаревшего ListView.

Схематично работу RecyclerView можно представить следующим образом. На экране отображаются видимые элементы списка. Когда при прокрутке списка верхний элемент уходит за пределы экрана и становится невидимым, его содержимое очищается. При этом сам "чистый" элемент помещается вниз экрана и заполняется новыми данными, иными словами переиспользуется, отсюда название Recycle.

![recyclerview20](https://github.com/gipnozhard/Exemple_RecyclerView/assets/71705375/7d1f0eb7-aaf4-4cf5-b3c2-58a33bb11265)

![recyclerview21](https://github.com/gipnozhard/Exemple_RecyclerView/assets/71705375/1f83e805-a682-4c4b-aa2d-c2780717672f)

Компонент RecyclerView не является родственником ListView и относится к семейству ViewGroup. Он часто используется как замена ListView, но его возможности шире.

Следует сказать, что при работе с ним приходится писать много кода, который пугает новичков. Если с RecyclerView работать не постоянно, то порой забываются детали и сложно вспомнить необходимые шаги. Многие просто сохраняют отдельные шаблоны и копируют в разные проекты.

Внешний вид можно представить не только в виде обычного списка, но и в виде сетки. При необходимости можно быстро переключиться между разными типами отображения.

Для размещения своих дочерних элементов используется специальный менеджер макетов LayoutManager. Он может быть трёх видов.

 * LinearLayoutManager - дочерние элементы размещаются вертикально (как в ListView) или горизонтально

 * GridLayoutManager - дочерние элементы размещаются в сетке, как в GridView

 * StaggeredGridLayoutManager - неравномерная сетка

Можно создать собственный менеджер на основе RecyclerView.LayoutManager.

RecyclerView.ItemDecoration позволяет работать с дочерними элементами: отступы, внешний вид.

ItemAnimator - отвечает за анимацию элементов при добавлении, удалении и других операций.

RecyclerView.Adapter связывает данные с компонентом и отслеживает изменения.

 * notifyItemInserted(), notifyItemRemoved(), notifyItemChanged() - методы, отслеживающие добавление, удаление или изменение позиции одного элемента

 * notifyItemRangeInserted(), notifyItemRangeRemoved(), notifyItemRangeChanged() - методы, отслеживающие изменение порядка элеметов

Стандартный метод notifyDataSetChanged() поддерживается, но он не приводит к внешнему изменению элементов на экране.

Программисты со стажем знают, что для создания "правильного" ListView нужно было создавать класс ViewHolder. В старых списках его можно было игнорировать. Теперь это стало необходимым условием.

Общая модель работы компонента.

![image](https://github.com/gipnozhard/Exemple_RecyclerView/assets/71705375/29c4a226-9fa7-425d-872a-7afdf0843450)

Мы рассмотрим только базовый пример для быстрого знакомства. В реальных проектах примеры будут гораздо сложнее, чтобы обеспечить другие функциональные возможности - обработка жестов, анимация, динамическое удаление и добавление элементов.

Размещаем компонент в макете экрана через панель инструментов. Но сначала добавим зависимость.

    implementation 'androidx.recyclerview:recyclerview:1.2.1'

Создадим макет для отдельного элемента списка. Варианты могут быть самыми разными - можно использовать один TextView для отображения строк (имена котов), можно использовать связку ImageView и TextView (имена котов и их наглые морды). Мы возьмём для примера две текстовые метки. Создадим новый файл res/layout/recyclerview_item.xml.

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
    
        <TextView
            android:id="@+id/textLarge"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textAppearance="?android:attr/textAppearanceLarge"
            tools:ignore="MissingConstraints"
            />
    
        <TextView
            android:id="@+id/textSmall"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textAppearance="?android:attr/textAppearanceSmall"
            tools:ignore="MissingConstraints"
            />
    
    </LinearLayout>

Добавим компонент в разметку экрана активности. activity_main.xml

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".model.MainActivity"
        >
    
        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:layout_marginBottom="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:listitem="@layout/recyclerview_item" 
            />
        
    </androidx.constraintlayout.widget.ConstraintLayout>

Если вы хотите видеть макет в режиме дизайна, то добавьте в RecyclerView атрибут tools:listitem="@layout/recyclerview_item". Тогда вам будет доступен предварительный просмотр.

Существуют и другие полезные атрибуты, которые вы можете применить к компоненту. Например, вы можете добавить полосу прокрутки:

    android:scrollbars="horizontal"

Минимальный код для запуска.

    // в onCreate()
    setContentView(R.layout.activity_main)
    
    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)

Если подключен viewBinding, то:

    // в onCreate()
    setContentView(binding.root)
    
    val recyclerView: RecyclerView = binding.recyclerView
    recyclerView.layoutManager = LinearLayoutManager(this)

Пока ничего сложного, но выводить такой список ничего не будет. Нужен адаптер и данные для отображения. В адаптере описывается способ связи между данными и компонентом.

Начнём по порядку, чтобы запомнить последовательность. Для начала создадим обычный класс и в конструкторе передадим список строк. Список будет содержать имена котов.

    package com.lavrent.exemplerecyclerview.adapter

    class RecyclerAdapter(private val names: List<String>) {

    }

Класс RecyclerViewHolder на основе ViewHolder служит для оптимизации ресурсов. Он служит своеобразным контейнером для всех компонентов, которые входят в элемент списка. При этом RecyclerView создаёт ровно столько контейнеров, сколько нужно для отображения на экране. Новый класс добавим в состав нашего созданного ранее класса. В скобках указываем название для элемента списка на основе View и этот же параметр указываем и для RecycleView.ViewHolder.

    package com.lavrent.exemplerecyclerview.adapter

    import android.view.View
    import androidx.recyclerview.widget.RecyclerView
    
    class RecyclerAdapter(private val names: List<String>) {
    
          class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
      
          }
    }  

В созданном классе-контейнере нужно просто перечислить используемые компоненты из макета для отдельного элемента списка. В нашем примере задействованы два TextView, инициализируем их через идентификаторы.

    package com.lavrent.exemplerecyclerview.adapter
    
    import android.view.View
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.lavrent.exemplerecyclerview.R
    
    class RecyclerAdapter(private val names: List<String>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {
    
        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            
            val largeText: TextView = itemView.findViewById(R.id.textLarge)
            val smallText: TextView = itemView.findViewById(R.id.textSmall)
        }
    }

Создадим адаптер - наследуем наш класс от класса RecyclerView.Adapter и в качестве параметра указываем созданный нами RecyclerViewHolder. Студия попросит реализовать три метода.

    package com.lavrent.exemplerecyclerview.adapter
    
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.lavrent.exemplerecyclerview.R
    
    class RecyclerAdapter(private val names: List<String>) :         RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {
    
        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            
            val largeText: TextView = itemView.findViewById(R.id.textLarge)
            val smallText: TextView = itemView.findViewById(R.id.textSmall)
        }
    
        override fun getItemCount(): Int {
            return names.size
        }
    
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
    
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return RecyclerViewHolder(itemView)
        }
    
        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.largeText.text = names[position]
            holder.smallText.text = "Котики"
        }
    }

### getItemCount()

Возвращает количество элементов списка. Как правило данные являются однотипными, например, список или массив строк. Адаптеру нужно знать, сколько элементов нужно предоставить компоненту, чтобы распределить ресурсы и подготовиться к отображению на экране. При работе с коллекциями или массивом мы можем просто вычислить его длину и передать это значение методу адаптера getItemCount(). В простых случаях мы можем записать код в одну строчку.

     override fun getItemCount(): Int {
        return names.size
    }
    
### onCreateViewHolder

Нам нужно указать идентификатор макета для отдельного элемента списка, созданный нами ранее в файле recyclerview_item.xml. А также вернуть наш объект класса ViewHolder. Допустим, устройство может отобразить на экране 9 элементов списка. RecyclerView создаст 11-12 элементов (с запасом). Неважно, каким большим будет ваш список, но все данные будут размещаться в тех же 11 элементах, автоматически меняя содержимое при прокрутке.

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
    
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return RecyclerViewHolder(itemView)
     }

### onBindViewHolder()

В методе адаптера onBindViewHolder() связываем используемые текстовые метки с данными - в одном случае это значения из списка, во втором используется одна и та же строка. Параметр position отвечает за позицию в списке (индекс), по которой можно получить нужные данные.

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.largeText.text = names[position]
        holder.smallText.text = "Котики"
    }

Добавим котов. Имена котов и кошек разместим в ресурсах в виде массива в файле:
res/values/strings.xml.

    <string-array name="cat_names">
        <item>Рыжик</item>
        <item>Барсик</item>
        <item>Мурзик</item>
        <item>Мурка</item>
        <item>Васька</item>
        <item>Томасина</item>
        <item>Кристина</item>
        <item>Пушок</item>
        <item>Дымка</item>
        <item>Кузя</item>
        <item>Китти</item>
        <item>Масяня</item>
        <item>Симба</item>
    </string-array>

Создадим новую функцию для получения списка котов из ресурсов и передадим его адаптеру.

    private fun getCatList(): List<String> {
        return this.resources.getStringArray(R.array.cat_names).toList()
    }
    
    recyclerView.adapter = CustomRecyclerAdapter(getNameList())





