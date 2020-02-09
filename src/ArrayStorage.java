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
        storage[position++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < position; i++) {
           if (storage[i].uuid.equals(uuid)) {
               return storage[i];
           }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < position; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, position - i - 1);
                storage[position - 1] = null;
                position--;
                return;
            }
        }
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
