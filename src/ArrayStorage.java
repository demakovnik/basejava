import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int position;

    void clear() {
        for (int i = 0; i < position; i++) {
            storage[i] = null;
        }
        position = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < position; i++) {
            if (r.equals(storage[i])) {
                showFailMessageInList(r.uuid);
                return;
            }
        }
        storage[position++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < position; i++) {
           if (storage[i].uuid.equals(uuid)) {
               return storage[i];
           }
        }
        showFailMessageOutOfList(uuid);
        return null;
    }

    void update(Resume r) {
        for (int i = 0; i < position; i++) {
            if (storage[i].equals(r)) {
                storage[i] = new Resume();
                return;
            }
        }
        showFailMessageOutOfList(r.uuid);
    }

    void delete(String uuid) {
        for (int i = 0; i < position; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[position - 1];
                storage[position - 1] = null;
                position--;
                return;
            }
        }
        showFailMessageOutOfList(uuid);
    }

    void showFailMessageOutOfList(String uuid) {
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
    }

    void showFailMessageInList(String uuid) {
        System.out.println("Резюме c uuid " + uuid + " уже есть в списке");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, position);
    }

    int size() {
        return position;
    }
}
