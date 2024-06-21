public class test {
        public static void main(String[] args) {
            Item<Integer> itemInt = new Item<Integer>(18);
            Item<Character> itemChar = new Item<Character>('n');

            itemInt.updateCount(18);
            itemInt.updateCount(13);

            itemChar.updateCount('z');
            itemChar.updateCount('b');

        }
    }

