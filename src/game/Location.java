package game;

public enum Location {
    FORREST {
        @Override
        public String toString() {
            return "Лес";
        }
    },
    PYATACHOKHOME{
        @Override
        public String toString() {
            return "Дом Пятачка";
        }
    },
    RABBITHOME{
        @Override
        public String toString() {
            return "Дом Кролика";
        }
    },
    PUCHHOME{
        @Override
        public String toString() {
            return "Дом Пуха";
        }
    },
    ROBINHOME{
        @Override
        public String toString() {
            return "Дом Кристофера Робина";
        }
    };
}