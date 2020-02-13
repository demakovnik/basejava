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

    void save(Resume resume) {
        if (position == storage.length) {
            System.out.println("Хранилище резюме заполнено");
            return;
        }
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                System.out.println("Резюме c uuid " + resume.getUuid() + " уже есть в списке");
                return;
            }
        }
        storage[position++] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
        return null;
    }

    void update(Resume resume) {
        if (get(resume.getUuid()) != null) {
            storage[returnIndexOfResume(resume)] = resume;
            return;
        }
    }

    void delete(String uuid) {
        if (get(uuid) != null) {
            storage[returnIndexOfResume(get(uuid))] = storage[position - 1];
            storage[position - 1] = null;
            position--;
            return;
        }
        return;
    }

    private int returnIndexOfResume(Resume resume) {
        if (get(resume.getUuid()) != null) {
            for (int i = 0; i < position; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    return i;
                }
            }
        }
        return -1;
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
