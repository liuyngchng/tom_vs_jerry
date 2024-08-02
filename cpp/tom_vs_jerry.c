/**
 * gcc -g  -o _gtk tom_vs_jerry.c `pkg-config --cflags --libs gtk+-2.0`
 */
#include <gtk/gtk.h>

/**
 * 每次移动 10 个像素
 */
#define _MV_OFFSET  	10

/**
 * 窗口宽度 单位：像素
 */
#define _WINDOW_WIDTH 	800

/**
 * 窗口高度 单位：像素
 */
#define _WINDOW_HEIGHT 	600

#define _MV_INTERVAL_MS 20*1000

/**
 * 顶层容器，放置两个可固定位置的图标
 */
static GtkWidget *fixed;

/**
 * 角色1 button
 */
static GtkWidget *jerry;

/**
 * 角色2 button
 */
static GtkWidget *tom;

static GtkWidget *status_bar;
static gint status_bar_id;

static int jerry_key;

static int tom_key;

struct Action {
	int role;
	int key;
};

/* *
 * Create a new hbox with an image and a label packed into it
 * and return the box.
 * */
static GtkWidget *pic_label_box( gchar     *pic_filename,
                                 gchar     *label_text )
{
    GtkWidget *box;
    GtkWidget *label;
    GtkWidget *image;

    /* Create box for image and label */
    box = gtk_hbox_new (FALSE, 0);
    gtk_container_set_border_width (GTK_CONTAINER (box), 2);

    /* Now on to the image stuff */
    image = gtk_image_new_from_file (pic_filename);

    /* Create a label for the button */
    label = gtk_label_new (label_text);

    /* Pack the image and label into the box */
    gtk_box_pack_start (GTK_BOX (box), image, FALSE, FALSE, 3);
    gtk_widget_show (image);
    gtk_widget_show (label);

    return box;
}

void on_button_clicked(GtkButton *button, gpointer data) {
    gchar *buff;
    static int count = 1;
    buff = g_strdup_printf ("I am here, are u OK? %d", count++);
    gtk_statusbar_push (GTK_STATUSBAR (status_bar), GPOINTER_TO_INT(data), buff);
    g_free (buff);
}


int mv_widget(int role, GtkWidget *widget, int x_offset, int y_offset) {
	gint x = widget->allocation.x;
	gint y = widget->allocation.y;
	gchar *buff;
	if((x + x_offset) < 0 ||  (x + x_offset) > _WINDOW_WIDTH) {
//		g_print("%s collide to left or right wall, (%d, %d)\n",
//			role == 0 ? "jerry": "tom", x, y);
		buff = g_strdup_printf ("%s collide to left or right wall (%d, %d)",
			role == 0 ? "jerry": "tom", x, y);
	} else if((y + y_offset) < 0 ||  (y + y_offset) > _WINDOW_HEIGHT-120) {
//		g_print("%s collide to up or down wall, (%d, %d)\n",
//			role == 0 ? "jerry": "tom", x, y);
		buff = g_strdup_printf ("%s collide to left or right wall (%d, %d)",
			role == 0 ? "jerry": "tom", x, y);
		return -2;
	} else {
		gtk_fixed_move (GTK_FIXED (fixed), widget, x + x_offset, y + y_offset);
		buff = g_strdup_printf ("%s moved to (%d, %d)",
			role == 0 ? "jerry": "tom", x + x_offset, y + y_offset
		);
	}
//	gchar *buff;
//	buff = g_strdup_printf ("%s move to (%d, %d)\n",
//		role == 0 ? "jerry": "tom", x + x_offset, y + y_offset
//	);
	gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
	g_free (buff);
	return 0;
}

// 上移
void mv_up(int role, GtkWidget * widget) {
	mv_widget(role, widget, 0, -_MV_OFFSET);
}

// 下移
void mv_dn(int role, GtkWidget * widget) {
	mv_widget(role, widget, 0, _MV_OFFSET);
}

// 左移
void mv_lft(int role, GtkWidget * widget) {
	mv_widget(role, widget, -_MV_OFFSET, 0);
}

// 右移
void mv_rgt(int role, GtkWidget * widget) {
	mv_widget(role, widget, _MV_OFFSET, 0);
}

gboolean mv_role_by_key_press(int role, int key) {
	switch(key) {
	    case 'w':
	    case 'W':
//	        g_print("%s move up\n", role? "tom": "jerry");
	        mv_up(role, jerry);
	        break;
	    case 'a':
	    case 'A':
//	    	g_print("%s move left\n", role? "tom": "jerry");
	        mv_lft(role, jerry);
	        break;
	    case 'd':
	    case 'D':
//	    case 68:
//		case 100:
//	    	g_print("%s move right\n", role? "tom": "jerry");
	        mv_rgt(role, jerry);
	        break;
	    case 's':
	    case 'S':
//	    	g_print("%s move down\n", role? "tom": "jerry");
	        mv_dn(role, jerry);
	        break;
	    case 'i':
	    case 'I':
	    case 65362:
//	    	g_print("%s move up\n", role? "tom": "jerry");
			mv_up(role, tom);
			break;
		case 'j':
		case 'J':
		case 65361:
//			g_print("%s move left\n", role? "tom": "jerry");
			mv_lft(role, tom);
			break;
		case 'l':
		case 'L':
		case 65363:
//			g_print("%s move right\n", role? "tom": "jerry");
			mv_rgt(role, tom);
			break;
		case 'k':
		case 'K':
		case 65364:
//			g_print("%s move down\n", role? "tom": "jerry");
			mv_dn(role, tom);
			break;
	    default:
//	    	g_print("nothing_done_for_key %c\n", key);
	    	gchar *buff;
			buff= g_strdup_printf ("nothing done for key %c", key);
			gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
			g_free (buff);
	    	break;
	}
	return FALSE;
}



//void* mv_role(void* tdt) {
//	struct Action *action = (struct Action *)tdt;
//	g_print("%s action started\n", action->role==0 ? "jerry":"tom");
//	while(1) {
//
//		if(action->key) {
//			mv_role_by_key_press(action->role, action->key);
//		}
//		g_usleep(_MV_INTERVAL_MS);
//	}
//	return NULL;
//}

void* mv_tom(void* tdt) {
//	g_print("tom_action_started\n");
	gchar *buff= g_strdup_printf ("tom action started");
	gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
	g_free (buff);
	while(1) {
		if(tom_key) {
			mv_role_by_key_press(1, tom_key);
		}else {
//			g_print("do_nothing_for_tom_action\n");
//			gchar *buff= g_strdup_printf ("do nothing for tom action");
//			gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
//			g_free (buff);
		}
		g_usleep(_MV_INTERVAL_MS);
	}
	return NULL;
}

void* mv_jerry(void* tdt) {
//	g_print("jerry_action_started\n");
	gchar *buff= g_strdup_printf ("jerry action started");
	gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
	g_free (buff);
	while(1) {
		if(jerry_key) {
			mv_role_by_key_press(0, jerry_key);
		} else {
//			g_print("do_nothing_for_jerry_action\n");
//			gchar *buff= g_strdup_printf ("do nothing for jerry action");
//			gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
//			g_free (buff);
		}
		g_usleep(_MV_INTERVAL_MS);
	}
	return NULL;
}

/**
 * 按键按下时触发事件
 */
gboolean on_key_pressed(GtkWidget *widget,
		GdkEventKey *event, gpointer user_data) {
//	g_print("_on_key_pressed %c\n", event->keyval);
	gchar *buff;
	switch(event->keyval) {
	    case 'w':
	    case 'W':
	    case 'a':
	    case 'A':
	    case 'd':
	    case 'D':
	    case 's':
	    case 'S':
	    	if(jerry_key !=event->keyval){
				jerry_key = event->keyval;
//				g_print("set_jerry_key_on_key_pressed %c\n", jerry_key);
				buff = g_strdup_printf ("set_jerry_key_on_key_pressed %c", jerry_key);
	    	}
	        break;
	    case 'i':
	    case 'I':
	    case 65362:
		case 'j':
		case 'J':
		case 65361:
		case 'l':
		case 'L':
		case 65363:
		case 'k':
		case 'K':
		case 65364:
			if(tom_key !=event->keyval){
				tom_key = event->keyval;
//				g_print("set_tom_key_on_key_pressed %c\n", tom_key);
				buff = g_strdup_printf ("set_tom_key_on_key_pressed %c", tom_key);
			}
			break;
	    default:
//	    	g_print("nothing_done_for_key_pressed %c\n", event->keyval);
	    	buff = g_strdup_printf ("nothing_done_for_key_pressed %c", event->keyval);
	    	break;
	}
	gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
	g_free (buff);
	return FALSE;
}

gboolean on_key_released(GtkWidget *widget,
		GdkEventKey *event, gpointer user_data) {
//	gchar *buff;
	switch(event->keyval) {
	    case 'w':
	    case 'W':
	    case 'a':
	    case 'A':
	    case 'd':
	    case 'D':
	    case 's':
	    case 'S':
	        jerry_key = 0;
//	        g_print("set_jerry_key_on_key_released, %d\n", jerry_key);
//			buff= g_strdup_printf ("set_jerry_key_on_key_released, %d", jerry_key);
	        break;
	    case 'i':
	    case 'I':
	    case 65362:
		case 'j':
		case 'J':
		case 65361:
		case 'l':
		case 'L':
		case 65363:
		case 'k':
		case 'K':
		case 65364:
			tom_key = 0;
//			g_print("set_tom_key_on_key_released, %d\n", tom_key);
//			buff= g_strdup_printf ("set_tom_key_on_key_released, %d", tom_key);
			break;
	    default:
	    	g_print("nothing_done_for_key_released %d\n", event->keyval);
	    	gchar *buff= g_strdup_printf ("nothing done for key released, %d", event->keyval);
	    	gtk_statusbar_push (GTK_STATUSBAR (status_bar), status_bar_id, buff);
			g_free (buff);
	    	break;
	}

    return FALSE;
}

int main(int argc, char *argv[]) {
	jerry_key = 0;
	tom_key = 0;
	g_thread_new("jerry_t", &mv_jerry, NULL);
//	g_thread_new("jerry_t", &mv_tom, NULL);
//	pthread_t t1;
//	pthread_create(&t1, NULL, &mv_jerry, NULL);
//	pthread_detach(t1);
//
//	pthread_t t2;
//	pthread_create(&t2, NULL, &mv_tom, NULL);
//	pthread_detach(t2);

    gtk_init(&argc, &argv);

    GtkWidget *window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_default_size(GTK_WINDOW(window), _WINDOW_WIDTH, _WINDOW_HEIGHT);
    gtk_window_set_title(GTK_WINDOW(window),"Tom and Jerry Game");
    GtkWidget *vbox = gtk_vbox_new (FALSE, 1);
    gtk_container_add (GTK_CONTAINER (window), vbox);
    fixed = gtk_fixed_new();
    jerry = gtk_button_new();

    tom = gtk_button_new();

    g_signal_connect(window, 	"destroy", G_CALLBACK(gtk_main_quit), NULL);
//    g_signal_connect(jerry, 	"clicked", G_CALLBACK(on_button_clicked), (gpointer)"Jerry");
//    g_signal_connect(tom, 		"clicked", G_CALLBACK(on_button_clicked), (gpointer)"Tom");

    g_signal_connect(window, 	"key_press_event", G_CALLBACK(on_key_pressed), (gpointer)"test");
	g_signal_connect(window, 	"key_release_event", G_CALLBACK(on_key_released), (gpointer)"test");

    GtkWidget *box1 = pic_label_box ("jerry.png", "Jerry");
    gtk_container_add (GTK_CONTAINER (jerry), box1);
    GtkWidget *box2 = pic_label_box ("tom.png", "Tom");
    gtk_container_add (GTK_CONTAINER (tom), box2);

    gtk_fixed_put(GTK_FIXED(fixed), jerry, 0, 300);
    gtk_fixed_put(GTK_FIXED(fixed), tom, 800, 300);
//    gtk_container_add(GTK_CONTAINER(vbox), fixed);
	gtk_box_pack_start(GTK_BOX (vbox), fixed, TRUE, TRUE, 0);
    // test move
//    gtk_fixed_move (GTK_FIXED (fixed), button1, 10, 20);
    status_bar = gtk_statusbar_new ();
    gtk_box_pack_start(GTK_BOX (vbox), status_bar, TRUE, TRUE, 0);
    status_bar_id = gtk_statusbar_get_context_id(GTK_STATUSBAR(status_bar), "status_bar");
    g_signal_connect(jerry, 	"clicked", G_CALLBACK(on_button_clicked), GINT_TO_POINTER (status_bar_id));
    g_signal_connect(tom, 		"clicked", G_CALLBACK(on_button_clicked), GINT_TO_POINTER (status_bar_id));
    gtk_widget_show_all(window);
    gtk_main();

    return 0;
}
