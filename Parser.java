import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class Parser {
	public final static String separators = " ,.!?\";:[]()\n\r\t";
	
	public static StringBuffer sb;
	public static Hashtable ht;
	public static String[] words;
	public static int[] lens;
	
	public static void add() {
		if (sb.length() > 0) {
			String s = sb.toString();
			if (ht.containsKey(s)) {
				int n = ((Integer) ht.get(s)).intValue();
				ht.put(s, new Integer(n + 1));
			} else {
				ht.put(s, new Integer(1));
			}
			sb = new StringBuffer();
		}
	}
	
	public static void swap(int i, int j) {
		int temp = lens[i];
		lens[i] = lens[j];
		lens[j] = temp;
		String tempstr = words[i];
		words[i] = words[j];
		words[j] = tempstr;
	}

	// Быстрая сортировка от элемента left до элемента right
	public static void qsort(int left, int right) {
		// Массивы из 1-ого элемента сортировать незачем
		if(left >= right)
			return;
		int l = left, r = right; // Левая и правая границы
		// Выбираем разделяющий элемент - может быть любым,
		// но выбор случайного элемента устойчив к худшим случаям
		int m = lens[(left + right) / 2];
		do {
			while (lens[l] > m)
				l++; // Двигаем левую границу
			while (lens[r] < m)
				r--; // Двигаем правую границу
			if (l <= r) {
				swap(l, r);
				l++;
				r--;
			} // Меняем элементы местами
		} while (l <= r);
		qsort(left, r); // Сортируем левую половину массива
		qsort(l, right); // Сортируем правую половину массива
	}
	
	public static void main(String args[]) {
		try {
			String address = args[0];
			InputStreamReader isr = new InputStreamReader(new FileInputStream(address), "UTF-8");
			int n;
			boolean tag = false, amp = false;
			sb = new StringBuffer();
			ht = new Hashtable();
			while ((n = isr.read()) != -1) {
				char ch = (char) n;
				if (ch == '<') {
					add();
					tag = true;
					continue;
				}
				if (ch == '&') {
					amp = true;
					continue;
				}
				if (ch == '>') {
					tag = false;
					continue;
				}
				if (amp && ch == ';') {
					amp = false;
					continue;
				}
				if (!tag && !amp) {
					if (separators.indexOf(ch) != -1) {
						add();
					} else {
						ch = Character.toUpperCase(ch);
						sb.append(ch);
					}
				}
			}
			add();
			int size = ht.size();
			words = new String[size];
			lens = new int[size];
			Enumeration keys = ht.keys();
			for (int i = 0; i < size; i++) {
				words[i] = (String) keys.nextElement();
				lens[i] = ((Integer) ht.get(words[i])).intValue();
			}
			qsort(0, size - 1);
			for (int i = 0; i < size; i++)
				System.out.println(words[i] + " - " + lens[i]);
			isr.close();
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
}