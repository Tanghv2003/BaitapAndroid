package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )
  private var recentlyDeletedStudent: StudentModel? = null
  private var recentlyDeletedPosition: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students,
      onEditClick = { student, position -> editStudent(student, position) },
      onDeleteClick = { student, position -> confirmDeleteStudent(student, position) }
    )

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      addNewStudent()
    }
  }

  private fun addNewStudent() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Add Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = dialogView.findViewById<EditText>(R.id.studentNameEditText).text.toString().trim()
        val mssv = dialogView.findViewById<EditText>(R.id.studentMssvEditText).text.toString().trim()
        if (name.isNotEmpty() && mssv.isNotEmpty()) {
          val newStudent = StudentModel(name, mssv)
          students.add(newStudent)
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun editStudent(student: StudentModel, position: Int) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
    val nameEditText = dialogView.findViewById<EditText>(R.id.studentNameEditText)
    val mssvEditText = dialogView.findViewById<EditText>(R.id.studentMssvEditText)

    nameEditText.setText(student.studentName)
    mssvEditText.setText(student.studentId)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        val name = nameEditText.text.toString().trim()
        val mssv = mssvEditText.text.toString().trim()
        if (name.isNotEmpty() && mssv.isNotEmpty()) {
          student.studentName = name
          student.studentId = mssv
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun confirmDeleteStudent(student: StudentModel, position: Int) {
    AlertDialog.Builder(this)
      .setTitle("Delete Student")
      .setMessage("Delete this student?")
      .setPositiveButton("Delete") { _, _ ->
        deleteStudent(student, position)
      }
      .setNegativeButton("Cancel", null)
      .create()
      .show()
  }

  private fun deleteStudent(student: StudentModel, position: Int) {
    recentlyDeletedStudent = student
    recentlyDeletedPosition = position
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    Snackbar.make(findViewById(R.id.recycler_view_students), "Student has been deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") { undoDelete() }
      .show()
  }

  private fun undoDelete() {
    recentlyDeletedStudent?.let { student ->
      recentlyDeletedPosition?.let { position ->
        students.add(position, student)
        studentAdapter.notifyItemInserted(position)
      }
    }
  }
}
