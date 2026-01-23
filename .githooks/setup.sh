#!/bin/bash
#
# Setup script to configure Git hooks
# Run this after cloning the repository: ./githooks/setup.sh
#

echo "Setting up Git hooks..."

# Configure Git to use .githooks directory
git config core.hooksPath .githooks

# Make hooks executable
chmod +x .githooks/pre-commit

echo "✅ Git hooks configured successfully!"
echo ""
echo "The following hooks are now active:"
echo "  - pre-commit: Spotless formatting check + context.md auto-update"
