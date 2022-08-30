public class ArrayDeque<T> {
    public class AList {
        public T[] item;
        public int nextfirst;
        public int nextlast;

        public AList() {
            this.item = (T[]) new Object[8];
            this.nextfirst = 3;
            this.nextlast = 4;
        }

        public AList(int sz) {
            this.item = (T[]) new Object[sz];
        }
    }

    public int size;
    public AList ls;
    public int t_length, f_length, l_length;
    public int f_bound, l_bound;

    /** Initialization */
    public ArrayDeque() {
        this.size = 0;
        ls = new AList();
        t_length = 8;
        f_length = 8;
        l_length = 8;
        f_bound = 0;
        l_bound = l_length-1;
    }

    public void resize(String method) {
        if (method == "up") {
            AList nw = new AList(this.t_length * 2);
            System.arraycopy(ls.item, 0, nw.item, 0, this.t_length);
            this.ls.item = nw.item;
        } else {
            AList nw = new AList(this.t_length / 2);
            System.arraycopy(ls.item, 0, nw.item, 0, this.t_length/2);
            this.ls.item = nw.item;
        }
    }

    public void addFirst(T t) {
        ls.item[ls.nextfirst] = t;
        this.size += 1;

        if (ls.nextfirst == f_bound) {
            if (f_length == t_length) {
                resize("up");
                t_length *= 2;
            }
            f_length *= 2;
            f_bound = f_length/2;
            ls.nextfirst = f_length/2 - 1 + f_length/4;
        } else {
            ls.nextfirst -= 1;
        }
    }

    public void addLast(T t) {
        ls.item[ls.nextlast] = t;
        this.size += 1;

        if (ls.nextlast == l_bound) {
            if (l_length == t_length) {
                resize("up");
                t_length *= 2;
            }
            l_length *= 2;
            l_bound = l_length - 1;
            ls.nextlast = l_length/2 + l_length/4;
        } else {
            ls.nextlast += 1;
        }
    }


    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        //print out first
        int temp_f_length = f_length;
        int temp_f_start = this.ls.nextfirst;
        while (temp_f_length >= 8) {
            int temp_f_bound = temp_f_length/2 - 1 + temp_f_length/4;
            for (int i = temp_f_start; i <= temp_f_bound; i++) {
                System.out.print(this.ls.item[i]);
            }
            temp_f_start = temp_f_length/2 != 8 ? temp_f_length/4 : 0;
            temp_f_length /= 2;
        }

        //print out last
        int temp_l_length = l_length;
        int temp_l_start = this.ls.nextlast;
        while (temp_l_length >= 8) {
            int temp_l_bound = temp_f_length/2 + temp_f_length/4;
            for (int i = temp_l_start; i >= temp_l_bound; i--) {
                System.out.print(this.ls.item[i]);
            }
            temp_l_start = temp_l_length/2 - 1;
            temp_l_length /= 2;
        }

        System.out.println();
    }

    public T removeFirst() {
        //handles the null situation
        if (this.ls.nextfirst == 3) {
            return null;
        }

        int temp_f_bound = f_length/2 - 1 + f_length/4;
        if (this.ls.nextfirst == temp_f_bound) {
            if (this.f_length == this.t_length && this.l_length < this.t_length) {
                //cut the size by half
                resize("down");
                t_length /= 2;
            }
            f_length /= 2;
            f_bound = f_length/2;
            this.ls.nextfirst = f_length/2;
        } else {
            this.ls.nextfirst += 1;
        }
        return this.ls.item[this.ls.nextfirst];
    }

    public T removeLast() {
        if (this.ls.nextlast == 4) {
            return null;
        }

        int temp_l_bound = l_length/2 + l_length/4;
        if (this.ls.nextlast == temp_l_bound) {
            if (this.l_length == this.t_length && this.f_length < this.t_length) {
                //cut the size by half
                resize("down");
                t_length /= 2;
            }
            l_length /= 2;
            l_bound = l_length - 1;
            this.ls.nextlast = l_length - 1;
        } else {
            this.ls.nextlast -= 1;
        }
        return this.ls.item[this.ls.nextlast];
    }

    public T get(int index) {
        if (this.size == 0) {
            return null;
        }

        int cur_ind, temp_bound, temp_length, cnt;
        int f_coverage = this.f_length == 8 ? 3 - this.ls.nextfirst : f_length/4 + (f_length/2 - 1 + f_length/4 - this.ls.nextfirst);
        index += 1;
        if (f_coverage >= index) {
            cur_ind = this.ls.nextfirst;
            temp_bound = f_length == 8 ? 3 : f_length/2 - 1 + f_length/4;
            temp_length = f_length;
            cnt = 0;
            while (cnt < index) {
                if (cur_ind == temp_bound) {
                    temp_length /= 2;
                    temp_bound = temp_length == 8 ? 3 : temp_length/2 - 1 + temp_length/4;
                    cur_ind = temp_length / 2;
                } else {
                    cur_ind += 1;
                }
                cnt += 1;
            }
        } else {
            index -= f_coverage;
            cur_ind = 4;
            temp_bound = 7;
            temp_length = 8;
            cnt = 1;
            while (cnt < index) {
                if (cur_ind == temp_bound) {
                    temp_length *= 2;
                    temp_bound = temp_length - 1;
                    cur_ind = temp_length/2 + temp_length/4;
                } else {
                    cur_ind += 1;
                }
                cnt += 1;
            }
        }
        return this.ls.item[cur_ind];
    }
}
