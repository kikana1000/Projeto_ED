package resources.classes;
import resources.interfaces.*;
import resources.exceptions.*;


    public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {
        public ArrayOrderedList() {
        }

        public void add(T item) {
            if (!(item instanceof Comparable)) {
                throw new UnsupportedDataTypeException("Not Comparable");
            } else {
                if (this.size() == this.array.length) {
                    this.expandCapacity();
                }

                Comparable comparable = (Comparable)item;
                if (this.isEmpty()) {
                    this.array[0] = item;
                } else if (comparable.compareTo(this.array[0]) <= 0) {
                    this.shiftRight(0, comparable);
                } else if (comparable.compareTo(this.array[this.modCount - 1]) > 0) {
                    this.array[this.modCount] = item;
                } else {
                    for(int x = 1; x < this.size(); ++x) {
                        if (comparable.compareTo(this.array[x]) <= 0) {
                            this.shiftRight(x, comparable);
                            break;
                        }
                    }
                }

                ++this.modCount;
            }
        }
    }


