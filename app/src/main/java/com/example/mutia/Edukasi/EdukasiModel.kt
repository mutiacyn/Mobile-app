package com.example.mutia.Edukasi

data class EdukasiModel(
    val title: String,
    val subtitle: String,
    val imageUrl: String
)

object EdukasiData {

    val tips = listOf(
        EdukasiModel("Istirahat Cukup", "Tidur 8 jam sehari untuk kesehatan ibu dan janin", "https://picsum.photos/seed/tips1/400/300"),
        EdukasiModel("Minum Air Putih", "Minimal 8-10 gelas sehari untuk cegah dehidrasi", "https://picsum.photos/seed/tips2/400/300"),
        EdukasiModel("Kelola Stres", "Lakukan relaksasi dan meditasi ringan", "https://picsum.photos/seed/tips3/400/300")
    )

    val nutrisi = listOf(
        EdukasiModel("Bayam", "Kaya folat untuk perkembangan janin", "https://picsum.photos/seed/bayam1/400/300"),
        EdukasiModel("Alpukat", "Lemak sehat dan serat", "https://picsum.photos/seed/alpukat1/400/300"),
        EdukasiModel("Salmon Matang", "Sumber omega-3, pastikan matang sempurna", "https://picsum.photos/seed/salmon1/400/300"),
        EdukasiModel("Telur Rebus", "Protein dan kolin", "https://picsum.photos/seed/telur1/400/300"),
        EdukasiModel("Kacang Almond", "Camilan sehat sumber vitamin E", "https://picsum.photos/seed/almond1/400/300"),
        EdukasiModel("Pisang", "Kalium untuk membantu menjaga energi", "https://picsum.photos/seed/pisang1/400/300"),
        EdukasiModel("Ubi Jalar", "Karbohidrat kompleks dan serat", "https://picsum.photos/seed/ubi1/400/300"),
        EdukasiModel("Susu Ibu Hamil", "Membantu memenuhi kebutuhan kalsium", "https://picsum.photos/seed/susu1/400/300"),
        EdukasiModel("Jeruk", "Vitamin C untuk daya tahan tubuh", "https://picsum.photos/seed/jeruk1/400/300"),
        EdukasiModel("Brokoli", "Zat besi, vitamin, dan serat", "https://picsum.photos/seed/brokoli1/400/300")
    )

    val aktivitas = listOf(
        EdukasiModel("Yoga Prenatal", "20 menit • Melemaskan otot dan relaksasi", "https://picsum.photos/seed/yoga1/400/300"),
        EdukasiModel("Jalan Santai", "30 menit • Olahraga ringan setiap pagi", "https://picsum.photos/seed/jalan1/400/300"),
        EdukasiModel("Senam Halin", "25 menit • Dipandu bidan atau instruktur", "https://picsum.photos/seed/senam1/400/300"),
        EdukasiModel("Latihan Kegel", "10 menit • Melatih otot dasar panggul", "https://picsum.photos/seed/kegel1/400/300"),
        EdukasiModel("Peregangan Ringan", "10 menit • MenuGroupi pegal punggung", "https://picsum.photos/seed/stretch1/400/300"),
        EdukasiModel("Berenang", "30 menit • Olahraga rendah benturan", "https://picsum.photos/seed/renang1/400/300")
    )
}
