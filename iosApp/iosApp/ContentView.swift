//
//  ContentView.swift
//  iosApp
//
//  Created by Md Didarul Islam on 21/10/2025.
//

import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                    .ignoresSafeArea()
    }
}

#Preview {
    ContentView()
}
