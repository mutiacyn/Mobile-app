package com.example.mutia.Edukasi

// emoji dipakai sebagai gambar item (tidak butuh internet)
data class EdukasiModel(
    val title: String,
    val subtitle: String,
    val emoji: String
)

object EdukasiData {

    val tips = listOf(
        EdukasiModel("Istirahat Cukup", "Tidur 8 jam sehari untuk kesehatan ibu dan janin", "😴"),
        EdukasiModel("Minum Air Putih", "Minimal 8-10 gelas sehari untuk cegah dehidrasi", "💧"),
        EdukasiModel("Kelola Stres", "Lakukan relaksasi dan meditasi ringan", "🌿")
    )

    val nutrisi = listOf(
        EdukasiModel("Bayam", "Kaya folat untuk perkembangan janin", "🥬"),
        EdukasiModel("Alpukat", "Lemak sehat dan serat", "🥑"),
        EdukasiModel("Salmon Matang", "Sumber omega-3, pastikan matang sempurna", "🐟"),
        EdukasiModel("Telur Rebus", "Protein dan kolin", "🥚"),
        EdukasiModel("Kacang Almond", "Camilan sehat sumber vitamin E", "🥜"),
        EdukasiModel("Pisang", "Kalium untuk membantu menjaga energi", "🍌"),
        EdukasiModel("Ubi Jalar", "Karbohidrat kompleks dan serat", "🍠"),
        EdukasiModel("Susu Ibu Hamil", "Membantu memenuhi kebutuhan kalsium", "🥛"),
        EdukasiModel("Jeruk", "Vitamin C untuk daya tahan tubuh", "🍊"),
        EdukasiModel("Brokoli", "Zat besi, vitamin, dan serat", "🥦")
    )

    val aktivitas = listOf(
        EdukasiModel("Yoga Prenatal", "20 menit • Melemaskan otot dan relaksasi", "🧘‍♀️"),
        EdukasiModel("Jalan Santai", "30 menit • Olahraga ringan setiap pagi", "🚶‍♀️"),
        EdukasiModel("Senam Hamil", "25 menit • Dipandu bidan atau instruktur", "🤸‍♀️"),
        EdukasiModel("Latihan Kegel", "10 menit • Melatih otot dasar panggul", "💪"),
        EdukasiModel("Peregangan Ringan", "10 menit • Mengurangi pegal punggung", "🙆‍♀️"),
        EdukasiModel("Berenang", "30 menit • Olahraga rendah benturan", "🏊‍♀️")
    )
}